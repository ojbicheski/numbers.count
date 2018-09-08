/**
 * Copyright (C) 2007 Free Software Foundation, Inc. <http://fsf.org/>
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package numbers.count;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(App.class, args);
		System.err.println(applicationContext);
	}

	@Value("${spring.redis.port}")
    private int redisPort;

    private RedisServer redisServer;

    @PostConstruct
    public void startRedis() throws IOException {
		System.out.println("starting redis...");
        redisServer = new RedisServer(redisPort);
		if (!redisServer.isActive()) {
			redisServer.start();
		}
		System.out.println("redis started.");
    }

    @PreDestroy
    public void stopRedis() {
		System.out.println("shutting down redis...");
		redisServer.stop();
		System.out.println("bye!");
    }
}