/**
 * Copyright (C) 2007 Free Software Foundation, Inc. <http://fsf.org/>
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package numbers.count.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import numbers.count.cache.Counter;
import numbers.count.cache.CounterRepository;
import numbers.count.exception.InternalErrorException;
import numbers.count.exception.ObjectNotFoundException;
import numbers.count.file.CounterFile;

/**
 * @author orlei
 *
 */
@Service
public class CounterService {
	
	@Autowired
	private CounterRepository repository;

	public void loadFile(String fileName) {
		CounterFile file = new CounterFile();
		
		try {
			file.fileName(fileName).open();
			
			while (file.hasNext()) {
				String number = file.next();
				
				Optional<Counter> counter = Optional.ofNullable(repository.findOne(number));
				
				if (counter.isPresent()) {
					counter.get().increase();
				} else {
					try {
						counter = Optional.of(new Counter().setNumber(file.next()).increase());
					} catch (NumberFormatException e) {
						continue;
					}
				}
				
				repository.save(counter.get());
			}
			
			System.out.println("Lines loaded: " + file.linesRead());
		} catch (IOException  e) {
			throw new RuntimeException(e);
		} finally {
			try {
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<Counter> findAll() {
		List<Counter> result = new ArrayList<>();
		
		repository.findAll().forEach(counter -> result.add(counter));
		result.sort(Comparator.comparing(Counter::getIndex));
		
		return result;
	}
	
	public Counter find(String number) {
		Optional<Counter> counter = Optional.ofNullable(repository.findOne(number));
		
		if (!counter.isPresent()) {
			throw new ObjectNotFoundException(
					"Number [".concat(number).concat("] was not found."));
		}

		return counter.get();
	}
	
	public void add(String number) {
		Optional<Counter> counter = Optional.of(repository.findOne(number));
		
		if (counter.isPresent()) {
			counter.get().increase();
		} else {
			try {
				counter = Optional.of(new Counter().setNumber(number).increase());
			} catch (NumberFormatException e) {
				throw new InternalErrorException("Value received: ".concat(number), e);
			}
		}
	}
}
