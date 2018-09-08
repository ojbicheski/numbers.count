/**
 * Copyright (C) 2007 Free Software Foundation, Inc. <http://fsf.org/>
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package numbers.count.config;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import numbers.count.service.CounterService;

/**
 * @author orlei
 *
 */
@Configuration
@ComponentScan("numbers.count")
@EnableRedisRepositories(basePackages = "numbers.count.cache")
@EnableScheduling
@PropertySource(value = { 
		"application.properties", 
		"application-${spring.profiles.active}.properties" })
public class AppConfig {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private CounterService service;
	
    @Value("${numbers.count.file}")
    private String fileName;

	@Value("${spring.profiles.active}")
	private String profile;
	
    @Value("${mock.test}")
    private boolean mock;

	@Value("${spring.redis.port}")
	private int redisPort;

	@Value("${spring.redis.host}")
	private String redisHost;
    
    private boolean available = false;
    
	/**
	 * @return the available
	 */
	public boolean isAvailable() {
		return available;
	}

	public void releaseSystem() {
		available = true;
	}
    
	/**
	 * @return the mock
	 */
	public boolean isMock() {
		return mock;
	}

	public boolean isNotEmbedded() {
		return Objects.nonNull(profile) && 
				"docker".equalsIgnoreCase(profile);
	}

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		if (isNotEmbedded()) {
	    	return new LettuceConnectionFactory(redisHost, redisPort);
		}
		
		return new JedisConnectionFactory();
	}
	 
	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
	    RedisTemplate<String, Object> template = new RedisTemplate<>();
	    template.setConnectionFactory(redisConnectionFactory());
	    return template;
	}
	
	@EventListener(ApplicationReadyEvent.class)
	public void afterStartup() {
		logger.info("*** Profile: " + profile + " ***");

		// Clear cache
		logger.info("Flushing Redis Cache...");
		redisTemplate().getConnectionFactory().getConnection().flushAll();
		logger.info("Redis Cache flushed.");
		
		// Load file
		logger.info("Loading file...");
		if (!isNotEmbedded()) {
			fileName = getClass().getResource(fileName).getFile();
		}
		logger.info("File name: " + fileName);

		service.loadFile(fileName);
		logger.info("File loaded.");
	}
}