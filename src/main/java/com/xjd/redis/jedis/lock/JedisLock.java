package com.xjd.redis.jedis.lock;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author elvis.xu
 * @since 2015-10-08 23:53
 */
public class JedisLock {
	protected JedisPool jedisPool;
	protected long defaultExpire;

	public LockObject lock(Jedis jedis, String key, String val, Long expireMilliseconds, long tryLockMilliseconds) {
		LockObject lockObject = new LockObject();
		lockObject.jedis = jedis;
		lockObject.key = key;
		lockObject.val = val;
		lockObject.expireMilliseconds = expireMilliseconds;

		String result = null;
		long exp, stime;
		while (true) {
			// 1
			if (expireMilliseconds == null) {
				result = jedis.set(key, val, "nx");
			} else {
				result = jedis.set(key, val, "nx", "px", expireMilliseconds);
			}
			if (result != null) {
				break;
			}
			// 2
			exp = jedis.pttl(key);
			if (exp == -2) {
				continue;
			} else {
				stime = tryLockMilliseconds;
				if (exp >= 0 && exp < stime) {
					stime = exp;
				}
				try {
					Thread.sleep(stime);
				} catch (InterruptedException e) {
					// do-nothing
				}
			}
		}
		return lockObject;
	}

	/**
	 * @param lockObject
	 * @return 正常解锁, 返回0; 锁过期, 返回1; 锁已被它人获取, 返回2
	 */
	public int unlock(LockObject lockObject) {
		// fixme
		return 0;
	}


	public static class LockObject {
		protected Jedis jedis;
		protected String key;
		protected String val;
		protected Long expireMilliseconds;

		public String getKey() {
			return key;
		}

		public String getVal() {
			return val;
		}

		public Long getExpireMilliseconds() {
			return expireMilliseconds;
		}
	}
}
