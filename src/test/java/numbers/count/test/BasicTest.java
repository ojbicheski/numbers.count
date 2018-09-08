/**
 * Copyright (C) 2007 Free Software Foundation, Inc. <http://fsf.org/>
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package numbers.count.test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import numbers.count.cache.Counter;
import numbers.count.exception.ObjectNotFoundException;
import numbers.count.service.CounterService;

/**
 * @author orlei
 *
 */
@Test
@ContextConfiguration(locations = { "classpath:spring-test-config.xml" })
public class BasicTest extends AbstractTestNGSpringContextTests {

	@Autowired
	CounterService service;

	@Autowired
	private RedisTemplate<String, Object> redis;

	@Test
	void testLoadFile() {
		redis.getConnectionFactory().getConnection().flushAll();

		service.loadFile(getClass().getResource("/numbers.txt").getFile());
		
		List<Counter> list = service.findAll();
		
		assertNotNull(list);
		assertFalse(list.isEmpty(), "List is not empty");
		assertEquals(list.size(), 61);
	}

	@Test(
			dependsOnMethods = { "testLoadFile" })
	void testGetNumberFound() {

		Counter counter = service.find("100");
		
		assertNotNull(counter);
		assertEquals(counter.getQuantity(), 3);
	}

	@Test(
			dependsOnMethods = { "testLoadFile" }, 
			expectedExceptions = { ObjectNotFoundException.class })
	void testGetNumberNotFound() {
		service.find("999");
	}
}
