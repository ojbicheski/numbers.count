/**
 * Copyright (C) 2007 Free Software Foundation, Inc. <http://fsf.org/>
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package numbers.count.rest.v1;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import numbers.count.cache.Counter;
import numbers.count.config.AppConfig;
import numbers.count.exception.SystemUnavailableException;
import numbers.count.service.CounterService;

/**
 * @author orlei
 *
 */
@RestController
@RequestMapping("/numbers")
public class Rest {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CounterService service;

	@Autowired
	private AppConfig config;
	
	@PostMapping(path = "/count/{number}")
    @ResponseBody
    public void post(@PathVariable("number") String number) {
		systemAvailable();
		
		service.add(number);
    }

	@GetMapping(path = "/count/{number}")
    @ResponseBody
    public Counter get(@PathVariable("number") String number) {
		systemAvailable();
		
    	return service.find(number);
    }

	@DeleteMapping(path = "/count/{number}")
    @ResponseBody
    public void delete(@PathVariable("number") String number) {
		systemAvailable();
		
    	service.delete(number);
    }

	@GetMapping(path = "/count")
    @ResponseBody
    public Map<String, Long> map() {
		systemAvailable();
		
		Map<String, Long> result = service.findAll().stream().collect(
				Collectors.toMap(Counter::getNumber, Counter::getQuantity));
    	return result;
    }

	@GetMapping(path = "/count/list")
    @ResponseBody
    public List<Counter> list() {
		systemAvailable();
		
    	return service.findAll();
    }
    
    @GetMapping(path = "/health")
    @ResponseBody
    public String health() {
        return "OK";
    }

	private void systemAvailable() {
		if (!config.isAvailable()) {
			logger.warn("The startup not finished.");
			throw new SystemUnavailableException("The startup not finished... wait a moment.");
		}
	}
}
