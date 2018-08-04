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

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.PooledExecutionServiceConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.impl.config.persistence.CacheManagerPersistenceConfiguration;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;

/**
 * @ClassName @{link DiskCache}
 * @Description 磁盘缓存
 *  基于{@link HeapCache} 堆缓存的调用方法，此类仅提供不同于堆缓存的配置
 * @author binh
 * @date 2018/08/01
 */
public class DiskCache {
    private static String cache_file_path = "F:\\bak";
    
    /**
     * EhCache  
     * 
     * 在JVM停止时，记得调用cacheManager.close()， 从而保证内存数据能dump到磁盘
     */
    
    private static CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
        //使用默认线程池
        .using(PooledExecutionServiceConfigurationBuilder.newPooledExecutionServiceConfigurationBuilder()
            .defaultPool("default", 1, 10).build())
        //磁盘文件存储位置
        .with(new CacheManagerPersistenceConfiguration(new File(cache_file_path)))
        .build(true);
    
    private static CacheConfigurationBuilder<String, String> cacheConfig = CacheConfigurationBuilder.newCacheConfigurationBuilder(
        String.class, 
        String.class, 
        ResourcePoolsBuilder.newResourcePoolsBuilder()
        .disk(100, MemoryUnit.MB, true)) //磁盘缓存
        .withDiskStoreThreadPool("default", 16)  //使用“default”线程池dump文件到磁盘
        .withExpiry(Expirations.timeToLiveExpiration(Duration.of(10, TimeUnit.SECONDS)))
        .withSizeOfMaxObjectGraph(3)
        .withSizeOfMaxObjectSize(1, MemoryUnit.KB)
        ;
    
    private static org.ehcache.Cache<String, String> ehcache = cacheManager.createCache("eh-cache", cacheConfig);
    
    public void close() {
        cacheManager.close();
    }
    
    /**
     * MapDB
     * 
     * 因为开启了事务，MapDB则开启了WAL。另外，操作完缓存后记得调用db.commit方法提交事务
     */
    DB db = DBMaker.fileDB(cache_file_path) // 数据存放位置
        .fileMmapEnable() //启用mmap
        .fileMmapEnableIfSupported() // 在支持的平台上启用mmap
        .fileMmapPreclearDisable() // 让mmap文件更快
        .cleanerHackEnable() // 一些bug处理
        .transactionEnable() // 启用事务
        .closeOnJvmShutdown()
        .concurrencyScale(16)
        .make();
    
    HTreeMap mdbCache = db.hashMap("mdb-cache")
        .expireMaxSize(1000)
        .expireAfterCreate(10, TimeUnit.SECONDS)
        .expireAfterUpdate(10, TimeUnit.SECONDS)
        .expireAfterGet(10, TimeUnit.SECONDS)
        .createOrOpen();
    
    public void commit() {
        db.commit();
    }
    
}
