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

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.PooledExecutionServiceConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.impl.config.persistence.CacheManagerPersistenceConfiguration;

/**
 * @ClassName @{link EhCacheApi}
 * @Description EhCache
 *
 * @author binh
 * @date 2018/08/06
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class EhCacheApi {
    
    private int dispatcherConcurrency = 4;
    
    /**
     * 磁盘缓存文件目录
     */
    private String cacheFilePath;
    
    private CacheManager cacheManager;
    
    private CacheConfigurationBuilder cacheConfig;
    
    private Cache ehcache;
    
    private CacheType cacheType;
    
    private static volatile boolean isloaded = false;
    
    private EhCacheApi() {
        
    }
    
    public static EhCacheApi instance() {
        return HolderClass.instance;
    }
    
    public static EhCacheApi instance(CacheType cacheType) {
        EhCacheApi api = HolderClass.instance;
        if (!isloaded) {
            synchronized (EhCacheApi.class) {
                if (!isloaded) {
                    loadConfig(api, cacheType); 
                }
            }
        }
        return api;
    }
    
    /**
     * the heap cache of ehcache cache 
     * @param key
     */
    public void put(String key, String value) {
        ehcache.put(key, value);
    }
    
    /**
     * the heap cache of ehcache cache 
     * @param key
     */
    public String get(String key) {
        
        return (String) ehcache.get(key);
    }
    
    public void refresh() {
        isloaded = false;
    }
    public EhCacheApi withRefresh() {
        isloaded = false;
        return this;
    }
    
    public void close() {
        cacheManager.close();
    }
    
    private static void loadConfig(EhCacheApi api, CacheType cacheType) {
        api.setCacheType(cacheType);
        api.init();
        isloaded = true;
    }

    public EhCacheApi init() {
        if (ehcache != null) {
            ehcache = null;
        }
        if (CacheType.DISK.equals(cacheType)) {
            initDiskCache();
        } else if (CacheType.HEAP.equals(cacheType)){
            initHeapCache();
        } else if (CacheType.OUT_HEAP.equals(cacheType)){
            initOutHeapCache();
        } else {
            initHeapCache();
        }
        
        return this;
    }

    private static class HolderClass { 
        private final static EhCacheApi instance = new EhCacheApi(); 
    } 
    
    private void initHeapCache() {
        /**
         * ehcache
         */
        cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build(true);
        
        cacheConfig = CacheConfigurationBuilder.newCacheConfigurationBuilder(
            String.class, 
            String.class, 
            // heap EntryUnit.ENTRIES 设置缓存的条目数量，当超出此数量时按照LRU进行缓存回收
            // heap(100, MemoryUnit.MB) 设置缓存的内存空间，超出按照LRU进行缓存回收
            ResourcePoolsBuilder.newResourcePoolsBuilder().heap(100, EntryUnit.ENTRIES)) 
            .withDispatcherConcurrency(dispatcherConcurrency)
            //timeToLiveExpiration设置TTL,没有TTI
            //timeToIdleExpiration设置TTL和TTI
            .withExpiry(Expirations.timeToLiveExpiration(Duration.of(10, TimeUnit.SECONDS)));
        
        ehcache = cacheManager.createCache("eh-heap-cache", cacheConfig);
    }
    
    private void initOutHeapCache() {
        
        cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build(true);
        /**
         * EhCache 堆外缓存 config
         * 堆外缓存不支持基于容量的缓存过期策略
         */
        cacheConfig = 
            CacheConfigurationBuilder.newCacheConfigurationBuilder(
            String.class, 
            String.class, 
            ResourcePoolsBuilder.newResourcePoolsBuilder()
            .offheap(100, MemoryUnit.MB))  //堆外缓存
            .withDispatcherConcurrency(dispatcherConcurrency)
            //timeToLiveExpiration设置TTL,没有TTI
            //timeToIdleExpiration设置TTL和TTI
            .withExpiry(Expirations.timeToLiveExpiration(Duration.of(10, TimeUnit.SECONDS)))
            .withSizeOfMaxObjectGraph(3)
            .withSizeOfMaxObjectSize(1, MemoryUnit.KB)
            ;
        
        ehcache = cacheManager.createCache("eh-heapout-cache", cacheConfig);
    }
    
    private void initDiskCache() {
        
        /**
         * EhCache  
         * 
         * 在JVM停止时，记得调用cacheManager.close()， 从而保证内存数据能dump到磁盘
         */
        cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
            //使用默认线程池
            .using(PooledExecutionServiceConfigurationBuilder.newPooledExecutionServiceConfigurationBuilder()
                .defaultPool("default", 1, 10).build())
            //磁盘文件存储位置
            .with(new CacheManagerPersistenceConfiguration(new File(cacheFilePath)))
            .build(true);
        
        cacheConfig = CacheConfigurationBuilder.newCacheConfigurationBuilder(
            String.class, 
            String.class, 
            ResourcePoolsBuilder.newResourcePoolsBuilder()
            .disk(100, MemoryUnit.MB, true)) //磁盘缓存
            .withDiskStoreThreadPool("default", 16)  //使用“default”线程池dump文件到磁盘
            .withExpiry(Expirations.timeToLiveExpiration(Duration.of(10, TimeUnit.SECONDS)))
            .withSizeOfMaxObjectGraph(3)
            .withSizeOfMaxObjectSize(1, MemoryUnit.KB)
            ;
        
        ehcache = cacheManager.createCache("eh-disk-cache", cacheConfig);
    }
    
    public enum CacheType {
        /**
         * 堆缓存
         */
        HEAP,
        /**
         * 磁盘缓存
         */
        DISK,
        /**
         * 堆外缓存
         */
        OUT_HEAP;
        
    }

    public String getCacheFilePath() {
        return cacheFilePath;
    }

    public void setCacheFilePath(String cacheFilePath) {
        this.cacheFilePath = cacheFilePath;
    }
    
    public EhCacheApi withCacheFilePath(String cacheFilePath) {
        this.cacheFilePath = cacheFilePath;
        return this;
    }

    public CacheType getCacheType() {
        return cacheType;
    }

    public void setCacheType(CacheType cacheType) {
        this.cacheType = cacheType;
    }
    
    public EhCacheApi withCacheType(CacheType cacheType) {
        this.cacheType = cacheType;
        return this;
    }

    public int getDispatcherConcurrency() {
        return dispatcherConcurrency;
    }

    public void setDispatcherConcurrency(int dispatcherConcurrency) {
        this.dispatcherConcurrency = dispatcherConcurrency;
    }
    
}
