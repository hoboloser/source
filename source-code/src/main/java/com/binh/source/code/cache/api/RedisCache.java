/**
 * Copyright (c) 
 * 
 * Revision History
 *
 * Date            Programmer              Notes
 * ---------    ---------------------  --------------------------------------------
 * 2018/08/02	       binh              Initial
 */
package com.binh.source.code.cache.api;

import redis.clients.jedis.Jedis;

/**
 * @ClassName @{link RedisCache}
 * @Description TODO
 *
 * @author binh
 * @date 2018/08/02
 */
public class RedisCache {
    private static Jedis jedis = null;
    
    static {
        
        jedis = new Jedis("192.168.137.168", 6379);
    }
    
    public static Jedis instance() {
        
        return jedis;
    }
}
