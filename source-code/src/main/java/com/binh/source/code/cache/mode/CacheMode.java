/**
 * Copyright (c) 
 * 
 * Revision History
 *
 * Date            Programmer              Notes
 * ---------    ---------------------  --------------------------------------------
 * 2018/08/03	       binh              Initial
 */
package com.binh.source.code.cache.mode;

import com.binh.source.code.cache.api.LocalCache;
import com.google.common.cache.Cache;

/**
 * @ClassName @{link CacheMode}
 * @Description 缓存使用模式
 *  缓存使用模式主要分两大类： Cache-Aside、Cache-As-SoR(Read-through、Write-through、Write-behind)
 *  
 *
 * SoR(System-of-record):记录系统，或者可以叫做数据源，即实际存储原始数据的系统
 * Cache:缓存，是SoR的快照数据，Cache访问的速度比SoR要快，放入Cache的目的是提升访问速度，减少回源到SoR的次数
 * 回源：即回到数据源头获取数据，Cache没有命中时，需要从SoR读取数据。
 * 
 * ---------------------------------------------
 * Code-Aside使用使用AOP模式去实现
 * 
 * 问题：
 * 对于Code-Aside，可能存在并发更新的情况，即如果多个应用实例同事更新，那么缓存怎么办？
 * 1.如果是用户维度，并发情况较小，可以不考虑，加上过期时间来解决即可。
 * 2.对于基础数据，可以考虑使用使用canal订阅binlog，来进行增量更新分布式缓存，这样不会存在缓存数据不一致的情况。但是缓存更新会存在延迟，
 *   而本地缓存可根据不一致容忍度设置合理的过期时间。
 * 3.读服务场景，可以考虑使用一致性哈希，将相同的操作负载均衡到同一实例，从而减少并发几率。或设置比较短的过期时间。
 * @author binh
 * @date 2018/08/03
 */
public class CacheMode {
    
    
    /**
     * Cache-Aside即业务代码围绕着Cache写，是由业务代码直接维护缓存。
     * 
     * 读场景:先从缓存获取数据，如果没有命中，则回源到SoR并将源数据放入缓存供下次读取使用。
     * 
     * 写场景：先将数据写入SoR，写入成功后立即将数据同步写入缓存
     *         或者先将数据写入SoR，写入成功后将缓存数据过期，下次读取时再加载缓存。
     */
    public Object asideGet(String key) {
        Cache<String, Object> cache = LocalCache.instance(key);
        //1.先从缓存中获取数据
        Object value = cache.getIfPresent(key);
        if (value == null) {
            //2.1如果缓存没有命中，则回源到SoR获取源数据
            value = loadFromSoR(key);
            //2.2将数据放入缓存，下次即可从缓存中获取数据
            cache.put(key, value);
        }
        return value;
    }
    /**
     * Cache-Aside 写
     * @param key
     * @param value
     */
    public void asideSet(String key, Object value) {
        //1. 先写入SoR
        wirteToSoR(key, value);
        Cache<String, Object> cache = LocalCache.instance(key);
        //2.执行成功后立即同步写入缓存
        cache.put(key, value);
    }
    
    /**
     * Cache-Aside 写
     * 使key失效
     * @param key
     * @param value
     */
    public void asideSetWithExpire(String key, Object value) {
        //1. 先写入SoR
        wirteToSoR(key, value);
        Cache<String, Object> cache = LocalCache.instance(key);
        //2.执行成功后立即同步写入缓存
        cache.invalidate(key);
    }
    
    /**------------------------------------------------------------------------------------- */
    
    /**
     * Cache-As-SoR
     * 即把Cache看作SoR，所有操作都是对Cache进行，然后Cache再委托给SoR进行真实的读/写。即业务代码中只看到Cache的操作，看不到关于SoR相关的代码。
     * 
     * 基于Guava Cache
     * 
     * Read-Through，业务代码首先调用Cache，如果不命中由Cache回源到SoR，而不是业务代码。
     * 需要搭配CacheLoader
     * @param key
     * @param value
     */
    public Object readThrough(String key) {
        return LocalCache.instanceRefresh(key).getIfPresent(key);
    }
    
    /**
     * Write-Through，穿透写模式/直写模式--业务代码首先调用Cache写（新增/修改）数据，然后由Cache负责写缓存和写SoR，而不是由业务代码。
     * 
     * 需要搭配CacheWriter组件
     * @param key
     * @param value
     */
    public void writeThrough(String key, Object value) {
        LocalCache.instanceWrite(key).put(key, value);
    }
    
    /**
     * Write-Behind，也叫Write-Back，称之为回写模式。不同于Write-Through是同步写SoR和Cache，Write-Behind是异步写。
     * 异步之后可以实现批量写、合并写、延时和限流。
     * @param key
     * @param value
     */
    public void writeBehind(String key, Object value) {
        LocalCache.instanceWriteBehind(key).put(key, value);
        
    }
    
    public void wirteToSoR(String key, Object value) {
        
    }
    
    public Object loadFromSoR(String key) {
        return new Object();
    }
}
