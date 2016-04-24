package org.hit.util;
import redis.clients.jedis.Jedis;
public class SendResult {
	GetConfigure getConfigure=new GetConfigure();
	public void send(String result){
		 Jedis jedis= RedisClientPool.getJedis();
		String  redis_result=getConfigure.getRedis_Result();
		jedis.sadd(redis_result,result);
		jedis.close();
	}
}
