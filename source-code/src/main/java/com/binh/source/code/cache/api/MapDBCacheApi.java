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

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;

/**
 * @ClassName @{link MapDBCacheApi}
 * @Description MapDBCacheApi
 *
 * 复合 & 单例  
 * @author binh
 * @date 2018/08/06
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class MapDBCacheApi<K, V> {
    
    /**
     * 并发级别
     */
    private int concurrencyScale = 16;
    /**
     * 缓存容量，超出以LRU进行回收
     */
    private int maxSize = 100;
    
    /**
     * 堆外缓存大小，默认64MB
     */
    private long storeSize = 64 * 1024 * 1024L; 
    /**
     * 设置TTL
     */
    private int create_TTL = 10 * 1000;
    
    private int update_TTL = 10 * 1000;
    /**
     * 设置TTI
     */
    private int get_TTI = 10 * 1000;
    
    /**
     * 磁盘缓存文件目录
     */
    private String cacheFilePath;
    /**
     * 线程池
     * 
     * 通过线程池定期进行缓存失效
     */
    private ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(Runtime.getRuntime().availableProcessors() / 2);
    
    private int executorPeriod = 3000;
    
    private CacheType cacheType;
    
    private HTreeMap<K, V> mapDBCache;
    
    private DB db;
    
    private static volatile boolean isloaded = false;
    
    public static MapDBCacheApi instance() {
        return HolderClass.instance;
    }
    
    public static MapDBCacheApi instance(CacheType cacheType) {
        MapDBCacheApi api = HolderClass.instance;
        if (!isloaded) {
            synchronized (MapDBCacheApi.class) {
                if (!isloaded) {
                    loadConfig(api, cacheType); 
                }
            }
        }
        return api;
    }
    
    public void refresh() {
        isloaded = false;
    }
    
    private static void loadConfig(MapDBCacheApi api, CacheType cacheType) {
        api.setCacheType(cacheType);
        api.init();
        isloaded = true;
    }

    private static class HolderClass { 
        private final static MapDBCacheApi instance = new MapDBCacheApi(); 
    } 
    
    public MapDBCacheApi() {
        
    }
    public MapDBCacheApi(CacheType cacheType) {
        this.cacheType = cacheType;
        if (CacheType.DISK.equals(cacheType)) {
            this.mapDBCache = initDiskCache();
        } else if (CacheType.HEAP.equals(cacheType)){
            this.mapDBCache = initHeapCache();
        } else if (CacheType.OUT_HEAP.equals(cacheType)){
            this.mapDBCache = initOutHeapCache();
        } else {
            this.mapDBCache = initHeapCache();
        }
    }    
    
    /**
     * the heap cache of mapdb cache 
     * @param key
     */
    public void put(K key, V value) {
        mapDBCache.put(key, value);
    }
    
    /**
     * the heap cache of mapdb cache 
     * @param key
     */
    public V get(K key) {
        
        return mapDBCache.get(key);
    }
    /**
     * the disk cache
     */
    public void commit() {
        if (db != null) {
            db.commit();
        }
    }
    
    public void init() {
        if (mapDBCache != null) {
            mapDBCache.clear();
            mapDBCache = null;
        }
        if (CacheType.DISK.equals(cacheType)) {
            this.mapDBCache = initDiskCache();
        } else if (CacheType.HEAP.equals(cacheType)){
            this.mapDBCache = initHeapCache();
        } else if (CacheType.OUT_HEAP.equals(cacheType)){
            this.mapDBCache = initOutHeapCache();
        } else {
            this.mapDBCache = initHeapCache();
        }
        isloaded = true;
    }
    /**
     * 初始化堆内缓存
     * @return
     */
    private HTreeMap initHeapCache() {
        return DBMaker.heapDB()
            .concurrencyScale(concurrencyScale) 
            .make().hashMap("mdb-heap-cache")
            .expireMaxSize(maxSize) 
            .expireAfterCreate(create_TTL, TimeUnit.MILLISECONDS)
            .expireAfterUpdate(update_TTL, TimeUnit.MILLISECONDS)
            .expireAfterGet(get_TTI, TimeUnit.MILLISECONDS)
            .expireExecutor(executor)
            .expireExecutorPeriod(executorPeriod)
            .create();
        
    }
    
    /**
     * 初始化堆外缓存
     * @return
     */
    private HTreeMap initOutHeapCache() {
        return DBMaker.memoryDirectDB()
            .concurrencyScale(concurrencyScale) 
            .make().hashMap("mdb-outheap-cache")
            .expireStoreSize(storeSize)
            .expireMaxSize(maxSize)
            .expireAfterCreate(create_TTL, TimeUnit.MILLISECONDS)
            .expireAfterUpdate(update_TTL, TimeUnit.MILLISECONDS)
            .expireAfterGet(get_TTI, TimeUnit.MILLISECONDS)
            .create()
            ;
    }
    
    /**
     * 初始化磁盘缓存
     * @return
     */
    private HTreeMap<K, V> initDiskCache() {
        /**
         * MapDB
         * 
         * 因为开启了事务，MapDB则开启了WAL。另外，操作完缓存后记得调用db.commit方法提交事务
         */
        db = DBMaker.fileDB(cacheFilePath) // 数据存放位置
            .fileMmapEnable() //启用mmap
            .fileMmapEnableIfSupported() // 在支持的平台上启用mmap
            .fileMmapPreclearDisable() // 让mmap文件更快
            .cleanerHackEnable() // 一些bug处理
            .transactionEnable() // 启用事务
            .closeOnJvmShutdown()
            .concurrencyScale(concurrencyScale)
            .make();
        
        HTreeMap mdbCache = db.hashMap("mdb-disk-cache")
            .expireMaxSize(maxSize)
            .expireAfterCreate(create_TTL, TimeUnit.MILLISECONDS)
            .expireAfterUpdate(update_TTL, TimeUnit.MILLISECONDS)
            .expireAfterGet(get_TTI, TimeUnit.MILLISECONDS)
            .createOrOpen();
        
        return mdbCache;
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

    public int getConcurrencyScale() {
        return concurrencyScale;
    }

    public void setConcurrencyScale(int concurrencyScale) {
        this.concurrencyScale = concurrencyScale;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public long getStoreSize() {
        return storeSize;
    }

    public void setStoreSize(long storeSize) {
        this.storeSize = storeSize;
    }

    public int getCreate_TTL() {
        return create_TTL;
    }

    public void setCreate_TTL(int create_TTL) {
        this.create_TTL = create_TTL;
    }

    public int getUpdate_TTL() {
        return update_TTL;
    }

    public void setUpdate_TTL(int update_TTL) {
        this.update_TTL = update_TTL;
    }

    public int getGet_TTI() {
        return get_TTI;
    }

    public void setGet_TTI(int get_TTI) {
        this.get_TTI = get_TTI;
    }

    public String getCacheFilePath() {
        return cacheFilePath;
    }

    public void setCacheFilePath(String cacheFilePath) {
        this.cacheFilePath = cacheFilePath;
    }

    public ScheduledExecutorService getExecutor() {
        return executor;
    }

    public void setExecutor(ScheduledExecutorService executor) {
        this.executor = executor;
    }

    public int getExecutorPeriod() {
        return executorPeriod;
    }

    public void setExecutorPeriod(int executorPeriod) {
        this.executorPeriod = executorPeriod;
    }

    public HTreeMap<K, V> getMapDBCache() {
        return mapDBCache;
    }

    public void setMapDBCache(HTreeMap<K, V> mapDBCache) {
        this.mapDBCache = mapDBCache;
    }

    public DB getDb() {
        return db;
    }

    public void setDb(DB db) {
        this.db = db;
    }

    public CacheType getCacheType() {
        return cacheType;
    }

    public void setCacheType(CacheType cacheType) {
        this.cacheType = cacheType;
    }
}
