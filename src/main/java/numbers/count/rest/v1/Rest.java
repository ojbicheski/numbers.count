/**
 * Copyright (C) 2007 Free Software Foundation, Inc. <http://fsf.org/>
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package numbers.count.rest.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import numbers.count.cache.Counter;
import numbers.count.service.CounterService;

/**
 * @author orlei
 *
 */
@RestController
@RequestMapping("/numbers")
public class Rest {

	@Autowired
	private CounterService service;
	
	@RequestMapping(path = "/count/{number}", method = RequestMethod.GET)
    @ResponseBody
    public Counter get(@PathVariable("number") String number) {
		Counter counter = service.find(number);
    	return counter;
    }

	@RequestMapping(path = "/count", method = RequestMethod.GET)
    @ResponseBody
    public List<Counter> get() {
    	return service.findAll();
    }
    
    @RequestMapping(path = "/health", method = RequestMethod.GET)
    @ResponseBody
    public String health() {
        return "OK";
    }

}
