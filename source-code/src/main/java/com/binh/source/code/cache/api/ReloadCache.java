/**
 * Copyright (c) 
 * 
 * Revision History
 *
 * Date            Programmer              Notes
 * ---------    ---------------------  --------------------------------------------
 * 2018/08/03	       binh              Initial
 */
package com.binh.source.code.cache.api;

import java.util.Map;
import java.util.Map.Entry;

import jersey.repackaged.com.google.common.collect.Maps;

/**
 * @ClassName @{link ReloadCache}
 * @Description 缓存再加载 or refresh
 *
 * @author binh
 * @date 2018/08/03
 */
public class ReloadCache {
    
    /**
     * 缓存刷新
     * @param key
     */
    public static Object refreshCache(String key) {
        
        
        
        return new Object();
    }
    
    /**
     * 缓存加载
     * @param key
     * @return
     */
    public static Object loadCache(String key) {
        
        return new Object();
    }
    /**
     * 单个写SoR
     * @param key
     * @param value
     */
    public static void writeSignleSoR(String key, Object value) {
        
    }
    /**
     * 批量写SoR
     * @param key
     * @param value
     */
    public static void writeAllSoR(Iterable<? extends Entry<? extends String, ? extends Object>> keys) {
        
        
    }
    
    /**
     * 单个delete
     * @param key
     */
    public static void deleteSignleSoR(String key) {
        
    }
    
    /**
     * 批量delete
     * @param key
     */
    public static void deleteAllSoR(Iterable<? extends String> keys) {
        
    }
    
    /**
     * 单个加载
     * @param arg0 
     */
    public static Object loadSignleSoR(String arg0) {
        return new Object();
    }
    
    /**
     * 批量加载
     */
    public static Map<String, Object> loadAllSoR(Iterable<? extends String> keys) {
        
        return Maps.newHashMap();
    }
}
