/**
 * Copyright (C) 2007 Free Software Foundation, Inc. <http://fsf.org/>
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package numbers.count.redis;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;

import redis.embedded.RedisServer;

/**
 * @author orlei
 *
 */
public class RedisEmbedded {
	
    @Value("${spring.redis.port}")
    private int redisPort;

    private RedisServer redisServer;

    @PostConstruct
    public void startRedis() throws IOException {
		System.out.println("starting redis...");
		redisServer = RedisServer.builder().
				port(redisPort).
				setting("maxmemory 128M").
				build();
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
