package org.hit.util;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;  
import redis.clients.jedis.JedisPoolConfig;  
public class RedisClientPool {
    private static JedisPool jedisPool;
    private static GetConfigure getConfigure = new GetConfigure();  
    /** 
     *   初始化Jedis 
     * <一句话功能简述> 
     * <功能详细描述> 
     * @return 
     * @see [类、类#方法、类#成员] 
     */  
    private static JedisPoolConfig initPoolConfig()  
    {  
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();  
        // 控制一个pool最多有多少个状态为idle的jedis实例  
        jedisPoolConfig.setMaxIdle(1000);  
        // 最大能够保持空闲状态的对象数  
        jedisPoolConfig.setMaxIdle(300);  
        // 超时时间  
        jedisPoolConfig.setMaxWaitMillis(1000);  
        // 在borrow一个jedis实例时，是否提前进行alidate操作；如果为true，则得到的jedis实例均是可用的；  
        jedisPoolConfig.setTestOnBorrow(true);  
        // 在还会给pool时，是否提前进行validate操作  
        jedisPoolConfig.setTestOnReturn(true);  
        return jedisPoolConfig;  
    }  
    /** 
     * 初始化jedis连接池 
     */  
    public static void init()  
    {  
        JedisPoolConfig jedisPoolConfig = initPoolConfig();  
		String host=getConfigure.getRedis_host();
		int port=Integer.parseInt(getConfigure.getRedis_port());
        int timeout=60000;
        // 构造连接池  
        jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout);  
    }  
    public static Jedis getJedis()
    {
		if( jedisPool!=null)
			return jedisPool.getResource();
		else{
			init();
		    return jedisPool.getResource();
		}
    }
}
