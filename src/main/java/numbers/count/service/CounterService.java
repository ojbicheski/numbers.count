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
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import numbers.count.cache.Counter;
import numbers.count.cache.CounterRepository;
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
				
				Counter counter = repository.findOne(number);
				
				if (Objects.nonNull(counter)) {
					counter.increase();
				} else {
					counter = new Counter().setNumber(file.next()).increase();
				}
				
				repository.save(counter);
			}
		} catch (IOException  e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Counter> findAll() {
		List<Counter> result = new ArrayList<>();
		
		repository.findAll().forEach(counter -> result.add(counter));
		result.sort(Comparator.comparing(Counter::getNumber));
		
		return result;
	}
	
	public Counter find(String number) {
		return repository.findOne(number);
	}
}
