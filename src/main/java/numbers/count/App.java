/**
 * Copyright (C) 2007 Free Software Foundation, Inc. <http://fsf.org/>
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package numbers.count;

import java.io.IOException;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import redis.embedded.RedisServer;

/**
 * @author orlei
 *
 */
@SpringBootApplication
public class App {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(App.class, args);
		System.err.println(applicationContext);
	}

	@Value("${spring.redis.port}")
	private int redisPort;

	@Value("${spring.profiles.active}")
	private String profile;

	private RedisServer redisServer;

	@PostConstruct
	public void startRedis() throws IOException {
		if (isNotEmbedded()) {
			return;
		}
		
		logger.info("starting redis...");
		
		redisServer = new RedisServer(redisPort);
		
		if (!redisServer.isActive()) {
			redisServer.start();
		}
		
		logger.info("redis started.");
	}

	@PreDestroy
	public void stopRedis() {
		if (isNotEmbedded()) {
			return;
		}
		
		logger.info("shutting down redis...");
		
		redisServer.stop();
		
		logger.info("bye!");
	}

	private boolean isNotEmbedded() {
		return Objects.nonNull(profile) && 
				"docker".equalsIgnoreCase(profile);
	}
}