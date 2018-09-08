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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import numbers.count.cache.Counter;
import numbers.count.cache.CounterRepository;
import numbers.count.config.AppConfig;
import numbers.count.exception.InternalErrorException;
import numbers.count.exception.ObjectNotFoundException;
import numbers.count.file.CounterFile;

/**
 * @author orlei
 *
 */
@Service
public class CounterService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private CounterRepository repository;
	
	@Autowired
	private AppConfig config;

	public void loadFile(String fileName) {
		CounterFile file = new CounterFile();
		
		try {
			file.fileName(fileName).open();
			
			while (file.hasNext()) {
				add(file.next());
			}
			config.releaseSystem();
			
			logger.info("Lines loaded: " + file.linesRead());
		} catch (IOException  e) {
			logger.error("Load file failed", e);
			throw new RuntimeException(e);
		} finally {
			try {
				file.close();
			} catch (IOException e) {
				logger.error("Close file failed", e);
			}
		}
	}
	
	public List<Counter> findAll() {
		List<Counter> result = new ArrayList<>();
		
		repository.findAll().forEach(counter -> result.add(counter));
		
		if (logger.isDebugEnabled()) {
			logger.debug("List size: " + result.size());
		}
		
		result.sort(Comparator.comparing(Counter::getIndex));
		
		return result;
	}
	
	public Counter find(String number) {
		Optional<Counter> counter = Optional.ofNullable(repository.findOne(number));
		
		if (!counter.isPresent()) {
			logger.warn("Number [".concat(number).concat("] was not found."));
			throw new ObjectNotFoundException(
					"Number [".concat(number).concat("] was not found."));
		}

		return counter.get();
	}
	
	public void add(String number) {
		Optional<Counter> counter = Optional.ofNullable(repository.findOne(number));
		
		if (counter.isPresent()) {
			counter.get().increase();
		} else {
			try {
				counter = Optional.of(new Counter().setNumber(number).increase());
			} catch (NumberFormatException e) {
				logger.error("Value received: ".concat(number), e);
				throw new InternalErrorException("Value received: ".concat(number), e);
			}
		}
		
		repository.save(counter.get());
	}

	public void delete(String number) {
		Counter counter = find(number);
		
		repository.delete(counter);
	}
}