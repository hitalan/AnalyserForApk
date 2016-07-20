package org.hit.util;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import java.util.List;
public class getInfoByRedis {

    public  static List<Object> getInfo()  {
    	 List<Object> result = null;
    	GetConfigure getConfigure=new GetConfigure();
    	//return getConfigure.getRedis_queue();
        try 
        {
            Jedis jedis = RedisClientPool.getJedis();
            String result_queue = getConfigure.getRedis_queue();
            Transaction tx = jedis.multi();
            tx.zrevrange(result_queue, 0, 0);
            tx.zremrangeByRank(result_queue, -1, -1);
            result = tx.exec();
            jedis.close();

        }
        catch (Exception e){
        	System.out.println("bad connection to the redis");
        	result = null;
           // e.printStackTrace();
        }
        finally{
            return result;  
       }
    }
    
    
    public  static String getResultInfo()  {
   	 List<Object> result = null;
   		GetConfigure getConfigure=new GetConfigure();
   		//return getConfigure.getRedis_queue();
       try 
       {
           Jedis jedis = RedisClientPool.getJedis();
           String result_queue = getConfigure.getRedis_Result();
           Transaction tx = jedis.multi();
           tx.zrevrange(result_queue, 0, 0);
           tx.zremrangeByRank(result_queue, -1, -1);
           result = tx.exec();
           jedis.close();

       }
       catch (Exception e){
           e.printStackTrace();
       }
           return result.toString();  
      }
       }
