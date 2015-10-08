package com.xjd.redis.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author elvis.xu
 * @since 2015-10-08 23:06
 */
public class JedisPoolDemo {
	public static void main(String[] args) {
		JedisPool jedisPool = new JedisPool("103.224.81.184", 6379);

		// Jedis jedis = null;
		// try {
		// jedis = jedisPool.getResource();
		// System.out.println(jedis.set("mykey", "haha", "nx", "px", 10000));
		// System.out.println(jedis.get("mykey"));
		// System.out.println(jedis.ttl("mykey"));
		// } finally {
		// if (jedis != null) {
		// jedis.close();
		// }
		// }

		try (Jedis jedis = jedisPool.getResource()) {
			System.out.println(jedis.set("mykey", "haha", "xx", "px", 10000));
			System.out.println(jedis.get("mykey"));
			System.out.println(jedis.ttl("mykey"));
		}

		jedisPool.destroy();

	}
}
