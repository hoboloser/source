/**
 * Copyright (c) 
 * 
 * Revision History
 *
 * Date            Programmer              Notes
 * ---------    ---------------------  --------------------------------------------
 * 2018/08/06	       binh              Initial
 */
package com.binh.source.code.cache.api;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * @ClassName @{link GuavaCacheApi}
 * @Description guava cache
 *
 * @author binh
 * @date 2018/08/06
 */
public class GuavaCacheApi {
    
    private int concurrencyLevel = 4;
    
    private long maximumSize = 100;
    
    private long expireTime = 10 * 1000;

    private GuavaCacheApi() {
        
    }
    
    public static GuavaCacheApi instance() {
        return HolderClass.instance;
    }
    
    private static class HolderClass { 
        private final static GuavaCacheApi instance = new GuavaCacheApi(); 
    } 
    
    /**
     * the heap cache of Gauva cache 
     * @param key
     * @param value
     */
    public void put(Object key, Object value) {
        cache.put(key, value);
    }
    
    /**
     * the heap cache of Gauva cache 
     * @param key
     */
    public Object get(Object key) {
        
        return cache.getIfPresent(key);
    }
    
    /**
     * gauva cache
     */
    private Cache<Object, Object> cache = CacheBuilder.newBuilder()
        .concurrencyLevel(concurrencyLevel)  // 并发级别，值越大并发能力越强
        .expireAfterWrite(expireTime, TimeUnit.MILLISECONDS) //定期回收
        .maximumSize(maximumSize) //缓存容量
        .softValues() //设置软引用缓存，JVM堆内存不足时，可以回收这部分对象
        .recordStats() //启动记录统计信息，如命中率
        .build();
}
