/**
 * Copyright (c) 
 * 
 * Revision History
 *
 * Date            Programmer              Notes
 * ---------    ---------------------  --------------------------------------------
 * 2018/08/02	       binh              Initial
 */
package com.binh.source.code.cache;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;

/**
 * @ClassName @{link MultistageCache}
 * @Description 多级缓存
 *
 * @author binh
 * @date 2018/08/02
 */
public class MultistageCache {

    DB db = DBMaker.fileDB("file-path") // 数据存放位置
        .fileMmapEnable() //启用mmap
        .fileMmapEnableIfSupported() // 在支持的平台上启用mmap
        .fileMmapPreclearDisable() // 让mmap文件更快
        .cleanerHackEnable() // 一些bug处理
        .transactionEnable() // 启用事务
        .closeOnJvmShutdown()
        .concurrencyScale(16)
        .make();
    
    HTreeMap diskCache = db.hashMap("mdb-cache")
        .expireMaxSize(1000)
        .expireAfterCreate(10, TimeUnit.SECONDS)
        .expireAfterUpdate(10, TimeUnit.SECONDS)
        .expireAfterGet(10, TimeUnit.SECONDS)
        .createOrOpen();
    
    private static ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(10);
    HTreeMap heapCache = DBMaker.heapDB()
        .concurrencyScale(16) //并发级别
        .make().hashMap("mdb-cache")
        .expireMaxSize(100) //缓存容量，超出以LRU进行回收
        .expireAfterCreate(10, TimeUnit.SECONDS) //设置TTL
        .expireAfterUpdate(10, TimeUnit.SECONDS) //设置TTL
        .expireAfterGet(10, TimeUnit.SECONDS)  //设置TTI
        .expireExecutor(executor) // 通过线程池定期进行缓存失效
        .expireExecutorPeriod(3000)
        .expireOverflow(diskCache) //当缓存溢出时存储到disk
        .create(); 
    
}
