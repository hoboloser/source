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

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.PooledExecutionServiceConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.builders.WriteBehindConfigurationBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.impl.internal.loaderwriter.writebehind.NonBatchingLocalHeapWriteBehindQueue;
import org.ehcache.spi.loaderwriter.BulkCacheLoadingException;
import org.ehcache.spi.loaderwriter.BulkCacheWritingException;
import org.ehcache.spi.loaderwriter.CacheLoaderWriter;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;

/**
 * @ClassName @{link LocalCache}
 * @Description TODO
 * 
 * @author binh
 * @date 2018/08/02
 */
public class LocalCache {

    private static Cache<String, Object> cache = CacheBuilder.newBuilder()
        .concurrencyLevel(4)  // 并发级别，值越大并发能力越强
        .expireAfterWrite(10, TimeUnit.SECONDS) //定期回收
        .maximumSize(100) //缓存容量
        .softValues() //设置软引用缓存，JVM堆内存不足时，可以回收这部分对象
        .recordStats() //启动记录统计信息，如命中率
        .build();
    
    private static Cache<String, Object> reloadCache = CacheBuilder.newBuilder()
        .concurrencyLevel(4)  // 并发级别，值越大并发能力越强
        .expireAfterWrite(10, TimeUnit.SECONDS) //定期回收
        .maximumSize(100) //缓存容量
        .softValues() //设置软引用缓存，JVM堆内存不足时，可以回收这部分对象
        .recordStats() //启动记录统计信息，如命中率
        /**
         * 如果缓存没有命中，则委托给CacheLoader，CacheLoader会回源到SoR查询源数据（返回值必须不为null，可以包装NULL对象），然后写入缓存
         * 
         * 使用CacheLoader的好处:
         * 1.应用业务代码更简洁，消除重复代码
         * 2.解决Dog-pile effect，即当某个缓存失效时，又有大量相同的请求没命中缓存，从而使请求同时到后端，导致后端压力太大，此时限定一个请求去拿。
         */
        .build(new CacheLoader<String, Object>() {
            
            @Override
            public Object load(final String key) throws Exception {
                 return ReloadCache.refreshCache(key);
            }
            
        });
    
    private static CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build(true);
    
    /**
     * Write-Through config
     */
    private static CacheConfigurationBuilder<String, Object> cacheConfig = CacheConfigurationBuilder.newCacheConfigurationBuilder(
        String.class, 
        Object.class, 
        // heap EntryUnit.ENTRIES 设置缓存的条目数量，当超出此数量时按照LRU进行缓存回收
        // heap(100, MemoryUnit.MB) 设置缓存的内存空间，超出按照LRU进行缓存回收
        ResourcePoolsBuilder.newResourcePoolsBuilder().heap(100, EntryUnit.ENTRIES)) 
        .withDispatcherConcurrency(4)
        //timeToLiveExpiration设置TTL,没有TTI
        //timeToIdleExpiration设置TTL和TTI
        .withExpiry(Expirations.timeToLiveExpiration(Duration.of(10, TimeUnit.SECONDS)))
        
        /**
         * EhCache需要配置一个CacheLoaderWriter,CacheLoaderWriter知道如何去写SoR
         * 当Cache需要写（新增/修改）数据时，首先调用CacheLoaderWriter来（立即）同步到SoR，成功后会更新缓存
         */
        .withLoaderWriter(new CacheLoaderWriter<String, Object>() {

            @Override
            public void delete(String arg0) throws Exception {
                ReloadCache.deleteSignleSoR(arg0);
            }

            @Override
            public void deleteAll(Iterable<? extends String> arg0) throws BulkCacheWritingException, Exception {
                ReloadCache.deleteAllSoR(arg0);
            }

            @Override
            public Object load(String arg0) throws Exception {
                 return ReloadCache.loadSignleSoR(arg0);
            }

            @Override
            public Map<String, Object> loadAll(Iterable<? extends String> arg0)
                throws BulkCacheLoadingException, Exception {
                 return ReloadCache.loadAllSoR(arg0);
            }

            @Override
            public void write(String arg0, Object arg1) throws Exception {
                ReloadCache.writeSignleSoR(arg0, arg1);
            }

            @Override
            public void writeAll(Iterable<? extends Entry<? extends String, ? extends Object>> arg0)
                throws BulkCacheWritingException, Exception {
                ReloadCache.writeAllSoR(arg0);
            }
            
        })
        ;
    
    private static org.ehcache.Cache<String, Object> writeEhcache = cacheManager.createCache("eh-cache", cacheConfig);

    
    private static CacheManager behindCacheManager = CacheManagerBuilder.newCacheManagerBuilder()
        //配置线程池，WriteBehindConfigurationBuilder在useThreadPool配置使用哪一个线程池
        .using(PooledExecutionServiceConfigurationBuilder.newPooledExecutionServiceConfigurationBuilder()
            .pool("wirteBehindPool", 1, 5).build())
        .build(true);
    
    private static CacheConfigurationBuilder<String, Object> behindCacheConfig = 
        CacheConfigurationBuilder.newCacheConfigurationBuilder(
        String.class, 
        Object.class, 
        // heap EntryUnit.ENTRIES 设置缓存的条目数量，当超出此数量时按照LRU进行缓存回收
        // heap(100, MemoryUnit.MB) 设置缓存的内存空间，超出按照LRU进行缓存回收
        ResourcePoolsBuilder.newResourcePoolsBuilder().heap(100, EntryUnit.ENTRIES)) 
        .withDispatcherConcurrency(4)
        .withExpiry(Expirations.timeToLiveExpiration(Duration.of(10, TimeUnit.SECONDS)))
        //配置WriteBehind如何操作SoR
        .withLoaderWriter(new CacheLoaderWriter<String, Object>() {

            @Override
            public void delete(String arg0) throws Exception {
                ReloadCache.deleteSignleSoR(arg0);
            }

            @Override
            public void deleteAll(Iterable<? extends String> arg0) throws BulkCacheWritingException, Exception {
                ReloadCache.deleteAllSoR(arg0);
            }

            @Override
            public Object load(String arg0) throws Exception {
                 return ReloadCache.loadSignleSoR(arg0);
            }

            @Override
            public Map<String, Object> loadAll(Iterable<? extends String> arg0)
                throws BulkCacheLoadingException, Exception {
                 return ReloadCache.loadAllSoR(arg0);
            }

            @Override
            public void write(String arg0, Object arg1) throws Exception {
                ReloadCache.writeSignleSoR(arg0, arg1);
            }

            @Override
            public void writeAll(Iterable<? extends Entry<? extends String, ? extends Object>> arg0)
                throws BulkCacheWritingException, Exception {
                ReloadCache.writeAllSoR(arg0);
            }
            
        })
        /**
         * 异步写
         */
        //配置WriteBehind策略 
        .add(
            //表示不进行批量处理，如配置，那么所有的批量处理操作都将会转成单个操作，即CacheLoaderWrite只需要实现write和delete即可
            //writeAll和deleteAll将会把批量操作委托给write和delete
            WriteBehindConfigurationBuilder.newUnBatchedWriteBehindConfiguration()
            //等待队列大小，内部使用 NonBatchingLocalHeapWriteBehindQueue
            .queueSize(20)
            //并发程度
            .concurrencyLevel(16)
            .useThreadPool("wirteBehindPool").build())
        ;
    
    private static org.ehcache.Cache<String, Object> writeBehindEhcache = behindCacheManager.createCache("eh-behind-cache", behindCacheConfig);
    
    public static Cache<String, Object> instance() {
        return cache;
    }
    
    public static Cache<String, Object> instance(String key) {
        return cache;
    }
    
    public static Cache<String, Object> instanceRefresh(String key) {
        return reloadCache;
    }
    
    public static org.ehcache.Cache<String, Object> instanceWrite(String key) {
        return writeEhcache;
    }
    
    public static org.ehcache.Cache<String, Object> instanceWriteBehind(String key) {
        return writeBehindEhcache;
    }
    
    public static void refresh(String key) {
//        cache.get(key, )
    }
}
