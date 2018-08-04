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

import java.util.concurrent.TimeUnit;

import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;

/**
 * @ClassName @{link HeapOutCache}
 * @Description 堆外缓存 
 *  基于{@link HeapCache} 堆缓存的调用方法，此类仅提供不同于堆缓存的配置
 *
 * @author binh
 * @date 2018/08/01
 */
public class HeapOutCache {
    /**
     * MapDB 堆外缓存
     */
    //在使用堆外缓存时，请记得添加JVM启动参数，如 -XX:MaxDirectMemorySize=10G;
    HTreeMap mdbCache = DBMaker.memoryDirectDB()
        .concurrencyScale(16) 
        .make().hashMap("mdb-cache")
        .expireStoreSize(64 * 1024 * 1024) //指定堆外缓存大小 64MB
        .expireMaxSize(1000)
        .expireAfterCreate(10, TimeUnit.SECONDS)
        .expireAfterUpdate(10, TimeUnit.SECONDS)
        .expireAfterGet(10, TimeUnit.SECONDS)
        .create()
        ;
    
    /**
     * EhCache 堆外缓存 config
     * 堆外缓存不支持基于容量的缓存过期策略
     */
    private static CacheConfigurationBuilder<String, String> cacheConfig = 
        CacheConfigurationBuilder.newCacheConfigurationBuilder(
        String.class, 
        String.class, 
        ResourcePoolsBuilder.newResourcePoolsBuilder()
        .offheap(100, MemoryUnit.MB))  //堆外缓存
        .withDispatcherConcurrency(4)
        //timeToLiveExpiration设置TTL,没有TTI
        //timeToIdleExpiration设置TTL和TTI
        .withExpiry(Expirations.timeToLiveExpiration(Duration.of(10, TimeUnit.SECONDS)))
        .withSizeOfMaxObjectGraph(3)
        .withSizeOfMaxObjectSize(1, MemoryUnit.KB)
        ;
}
