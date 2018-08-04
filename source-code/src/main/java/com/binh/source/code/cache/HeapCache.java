/**
 * Copyright (c) 
 * 
 * Revision History
 *
 * Date            Programmer              Notes
 * ---------    ---------------------  --------------------------------------------
 * 2018/08/01	       binh              Initial
 */
package com.binh.source.code.cache;

import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * @ClassName @{link Cache}
 * @Description 堆缓存
 * 
 * 分别包含： Gauva Cache、EhCache 、MapDB
 * @author binh
 * @date 2018/08/01
 */
public class HeapCache {
    
    private static String INVALIDATE_KEY = "KEY:A";
    
    /**
     * gauva cache
     */
    private static Cache<String, String> cache = CacheBuilder.newBuilder()
        .concurrencyLevel(4)  // 并发级别，值越大并发能力越强
        .expireAfterWrite(10, TimeUnit.SECONDS) //定期回收
        .maximumSize(100) //缓存容量
        .softValues() //设置软引用缓存，JVM堆内存不足时，可以回收这部分对象
        .recordStats() //启动记录统计信息，如命中率
        .build();
    
    /**
     * ehcache
     */
    private static CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build(true);
    
    private static CacheConfigurationBuilder<String, String> cacheConfig = CacheConfigurationBuilder.newCacheConfigurationBuilder(
        String.class, 
        String.class, 
        // heap EntryUnit.ENTRIES 设置缓存的条目数量，当超出此数量时按照LRU进行缓存回收
        // heap(100, MemoryUnit.MB) 设置缓存的内存空间，超出按照LRU进行缓存回收
        ResourcePoolsBuilder.newResourcePoolsBuilder().heap(100, EntryUnit.ENTRIES)) 
        .withDispatcherConcurrency(4)
        //timeToLiveExpiration设置TTL,没有TTI
        //timeToIdleExpiration设置TTL和TTI
        .withExpiry(Expirations.timeToLiveExpiration(Duration.of(10, TimeUnit.SECONDS)));
    
    private static org.ehcache.Cache<String, String> ehcache = cacheManager.createCache("eh-cache", cacheConfig);

    
    /**
     * mapdb
     */
    private static ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(10);
    private static HTreeMap mdbCache = DBMaker.heapDB()
        .concurrencyScale(16) //并发级别
        .make().hashMap("mdb-cache")
        .expireMaxSize(100) //缓存容量，超出以LRU进行回收
        .expireAfterCreate(10, TimeUnit.SECONDS) //设置TTL
        .expireAfterUpdate(10, TimeUnit.SECONDS) //设置TTL
        .expireAfterGet(10, TimeUnit.SECONDS)  //设置TTI
        .expireExecutor(executor) // 通过线程池定期进行缓存失效
        .expireExecutorPeriod(3000)
        .create(); 
    
    /**
     * the heap cache of Gauva cache 
     * @param key
     * @param value
     */
    public static void heapCache(String key, String value) {
        cache.put(key, value);
    }
    
    /**
     * the heap cache of Gauva cache 
     * @param key
     */
    public static String getHeapCache(String key) {
        
        return cache.getIfPresent(key);
    }
    
    /**
     * the heap cache of ehcache cache 
     * @param key
     */
    public static void ehHeapCache(String key, String value) {
        ehcache.put(key, value);
    }
    
    /**
     * the heap cache of ehcache cache 
     * @param key
     */
    public static String getEhHeapCache(String key) {
        
        return ehcache.get(key);
    }
    
    /**
     * the heap cache of mapdb cache 
     * @param key
     */
    public static void mdbHeapCache(Object key, Object value) {
        mdbCache.put(key, value);
    }
    
    /**
     * the heap cache of mapdb cache 
     * @param key
     */
    public static Object getMdbHeapCache(Object key) {

        return mdbCache.get(key);
    }
    
    /** ------------------------------------- test ---------------------------------------------------------- */
    private static String[] keys;
    private static String key0;
    private static String key1;
    private static String key2;
    private static long sleep0 = 6000;
    private static long sleep1 = 4000;
    private static long sleep2 = 1000;
    static {
        Random random = new Random();
        keys = new String[100];
        for (int i = 0; i < 100; i++) {
            keys[i] = "KEY:" + random.nextInt(1000);
        }
        key0 = keys[6];
        key1 = keys[66];
        key2 = keys[99];
        
    }
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("-------- cache test begin --------");
        System.out.println("-------- gauva cache test begin --------");
        for (String key : keys) {
            heapCache(key, key);
        }
        System.out.println("gauva cache size : " + cache.size());
        System.out.println("gauva cache key0 : " + getHeapCache(key0));
        System.out.println("gauva cache key1 : " + getHeapCache(key1));
        System.out.println("gauva cache key2 : " + getHeapCache(key2));
        System.out.println(" --- gauva cache sleep0 : " + sleep0);
        Thread.sleep(sleep0);
        System.out.println("gauva cache size : " + cache.size());
        System.out.println("gauva cache key0 : " + getHeapCache(key0));
        System.out.println("gauva cache key1 : " + getHeapCache(key1));
        System.out.println("gauva cache key2 : " + getHeapCache(key2));
        System.out.println(" --- gauva cache sleep1 : " + sleep1);
        Thread.sleep(sleep1);
        System.out.println("gauva cache size : " + cache.size());
        System.out.println("gauva cache key0 : " + getHeapCache(key0));
        System.out.println("gauva cache key1 : " + getHeapCache(key1));
        System.out.println("gauva cache key2 : " + getHeapCache(key2));
        System.out.println(" --- gauva cache sleep2 : " + sleep2);
        Thread.sleep(sleep2);
        System.out.println("gauva cache size : " + cache.size());
        System.out.println("gauva cache key0 : " + getHeapCache(key0));
        System.out.println("gauva cache key1 : " + getHeapCache(key1));
        System.out.println("gauva cache key2 : " + getHeapCache(key2));
        System.out.println("-------- gauva cache test end --------");
        
        
        System.out.println("-------- ehHeapCache test begin --------");
        for (String key : keys) {
            ehHeapCache(key, key);
        }
        System.out.println("ehHeapCache key0 : " + getEhHeapCache(key0));
        System.out.println("ehHeapCache key1 : " + getEhHeapCache(key1));
        System.out.println("ehHeapCache key2 : " + getEhHeapCache(key2));
        System.out.println(" --- ehHeapCache sleep0 : " + sleep0);
        Thread.sleep(sleep0);
        System.out.println("ehHeapCache key0 : " + getEhHeapCache(key0));
        System.out.println("ehHeapCache key1 : " + getEhHeapCache(key1));
        System.out.println("ehHeapCache key2 : " + getEhHeapCache(key2));
        System.out.println(" --- ehHeapCache sleep1 : " + sleep1);
        Thread.sleep(sleep1);
        System.out.println("ehHeapCache key0 : " + getEhHeapCache(key0));
        System.out.println("ehHeapCache key1 : " + getEhHeapCache(key1));
        System.out.println("ehHeapCache key2 : " + getEhHeapCache(key2));
        System.out.println(" --- ehHeapCache sleep2 : " + sleep2);
        Thread.sleep(sleep2);
        System.out.println("ehHeapCache key0 : " + getEhHeapCache(key0));
        System.out.println("ehHeapCache key1 : " + getEhHeapCache(key1));
        System.out.println("ehHeapCache key2 : " + getEhHeapCache(key2));
        System.out.println("-------- ehHeapCache test end --------");
        
        System.out.println("-------- mdbHeapCache test begin --------");
        for (String key : keys) {
            mdbHeapCache(key, key);
        }
        System.out.println("mdbHeapCache size : " + mdbCache.size());
        System.out.println("mdbHeapCache key0 : " + getMdbHeapCache(key0));
        System.out.println("mdbHeapCache key1 : " + getMdbHeapCache(key1));
        System.out.println("mdbHeapCache key2 : " + getMdbHeapCache(key2));
        System.out.println(" --- mdbHeapCache sleep0 : " + sleep0);
        Thread.sleep(sleep0);
        System.out.println("mdbHeapCache size : " + mdbCache.size());
        System.out.println("mdbHeapCache key0 : " + getMdbHeapCache(key0));
        System.out.println("mdbHeapCache key1 : " + getMdbHeapCache(key1));
        System.out.println("mdbHeapCache key2 : " + getMdbHeapCache(key2));
        System.out.println(" --- mdbHeapCache sleep1 : " + sleep1);
        Thread.sleep(sleep1);
        System.out.println("mdbHeapCache size : " + mdbCache.size());
        System.out.println("mdbHeapCache key0 : " + getMdbHeapCache(key0));
        System.out.println("mdbHeapCache key1 : " + getMdbHeapCache(key1));
        System.out.println("mdbHeapCache key2 : " + getMdbHeapCache(key2));
        System.out.println(" --- mdbHeapCache sleep2 : " + sleep2);
        Thread.sleep(sleep2);
        System.out.println("mdbHeapCache size : " + mdbCache.size());
        System.out.println("mdbHeapCache key0 : " + getMdbHeapCache(key0));
        System.out.println("mdbHeapCache key1 : " + getMdbHeapCache(key1));
        System.out.println("mdbHeapCache key2 : " + getMdbHeapCache(key2));
        System.out.println("-------- ehHeapCache test end --------");
        
        
        System.out.println("-------- cache test end --------");
    }
}
