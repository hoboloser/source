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

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheLoader;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @ClassName @{link CacheAPI}
 * @Description 多级缓存api封装
 * 
 * 在同时使用本地缓存和分布式缓存时，本地缓存过期时间使用分布式缓存过期时间的一半，
 * 防止本地缓存数据缓存时间太长造成多实例间的数据不一致。
 *
 * @author binh
 * @date 2018/08/02
 */
public class CacheAPI {
    
    private Logger logger = LoggerFactory.getLogger(CacheAPI.class);
    /**
     * 是否写本地缓存
     */
    private boolean writeLocalCache = true;
    /**
     * 是否写分布式缓存
     */
    private boolean writeRemoteCache = true;
    
    /**
     * 是否读本地缓存
     */
    private boolean readLocalCache = true;
    
    /**
     * 是否读分布式缓存
     */
    private boolean readRemoteCache = true;
    
    /**
     * 是否强制获取最新数据
     */
    private boolean isForceUpdate = true;
    
    /**
     * NULL对象
     */
    private static final String NULL_STRING = new String();
    
    private ExecutorService asyncExcutors = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    
    /**
     * 写缓存
     * @param key
     * @param value
     * @param remoteCacheExpiresSeconds
     */
    public void set(final String key, final Object value, final int remoteCacheExpiresSeconds) {
        
        if (value == null) {
            return ;
        }
        
        //复制值对象
        //本地缓存是引用，分布式缓存需要序列化
        //如果不复制的话，则假设数据更改后将造成本地缓存与分布式缓存不一致
        //数据更改后 ：本地缓存将是最新的，但分布式缓存却是上一次的数据
        final Object finalValue = copy(value);
        //如果配置了写本地缓存，则根据key获取相关的本地缓存，然后写入
        if (writeLocalCache) {
            Cache<String, Object> localCache = LocalCache.instance(key);
            if (localCache != null) {
                localCache.put(key, finalValue);
            }
        }
        //如果配置了不写分布式缓存，则直接返回
        if (!writeRemoteCache) {
            return;
        }
        //异步更新分布式缓存
        asyncExcutors.execute(() -> {
            try {
                RedisCache.instance().setex(key, remoteCacheExpiresSeconds, JSON.toJSONString(finalValue));
            } catch (Exception e) {
                logger.info("redis cache error key, key : {}, {}", key, e.getMessage());
            }
        }); 
    }
    
    /**
     * 读单个缓存
     * @param key
     * @return
     */
    public Object get(String key) {
        Object value = null;
        if (readLocalCache) {
            Cache<String, Object> localCache = LocalCache.instance(key);
            if (localCache != null) {
                value = localCache.getIfPresent(key);
            }
        }
        
        if (!readRemoteCache) {
            return value;
        }
        
        if (value != null) {
            return value;
        }
        value = RedisCache.instance().get(key);
        
        return value;
    }
    
    public Map getKeys(List<String> keys, List<Class> types) {
        
        Map<String, Object> result = Maps.newHashMap();
        List<String> missKeys = Lists.newArrayList();
        List<Class> missTypes = Lists.newArrayList();
        
        if (readLocalCache) {
            for (int i = 0; i < keys.size(); i ++) {
                Cache<String, Object> localCache = LocalCache.instance(keys.get(i));
                if (localCache != null) {
                    Object value = localCache.getIfPresent(keys.get(i));
                    if (value == null) {
                        missKeys.add(keys.get(i));
                        missTypes.add(types.get(i));
                    } else {
                        result.put(keys.get(i), value);
                    }
                } else {
                    missKeys.add(keys.get(i));
                    missTypes.add(types.get(i));
                }
            }
        }
        
        if (!readRemoteCache) {
            return result;
        }
        final Map<String, Object> missResult = Maps.newHashMap();
        //对key分区，避免一次性调用太多
        final List<List<String>> keysPage = Lists.partition(missKeys, 10);
        //异步的方式获取
        List<Future<Map<String, Object>>> pageFutures = Lists.newArrayList();
        
        try {
            for (final List<String> partitionKeys : keysPage) {
    //            pageFutures.add(asyncExcutors.submit(() -> 
    //                RedisCache.instance().mget((String[]) (partitionKeys.toArray()))));
            }
            
            for (Future future : pageFutures) {
    //            missResult.putAll(future.get(3000, TimeUnit.MILLISECONDS));
            }
        } catch (Exception e) {
            pageFutures.forEach(future -> future.cancel(true));
            logger.error("error get remote cache");
        }
        
        //合并missResult和result
        result.putAll(missResult);
        return result;
    }
    
    /**
     * put NULL Cache
     */
    public void setNullCache(String key) {
        String value = loadDB();
        
        //如果DB没有数据，则将其封装为NULL_STRING并放入缓存
        if (value == null) {
            value = NULL_STRING;
        }
        Cache<String, Object> localCache = LocalCache.instance(key);
        localCache.put(key, value);
    }

    /**
     * get NULL Cache
     */
    public Object getNullCache(String key) {
        Cache<String, Object> localCache = LocalCache.instance(key);
        Object value = localCache.getIfPresent(key);
        //读取数据时，如果发现是NULL对象，则返回null，而不是回源到DB
        if (value == NULL_STRING) {
            return null;
        }
        return value;
    }
    
    /**
     * 强制获取最新数据
     */
    public void getWithRefresh(String key) {
        if (isForceUpdate) {
            Cache<String, Object> localCache = LocalCache.instance(key);
            CacheLoader<K, V>
        }
    }
    
    private String loadDB() {
        
        return null;
    }
    
    private Object copy(Object value) {
        Object finalObj = null;
        BeanUtils.copyProperties(value, finalObj);
        return finalObj;
    }
    
}
