/**
 * Copyright (c) 
 * 
 * Revision History
 *
 * Date            Programmer              Notes
 * ---------    ---------------------  --------------------------------------------
 * 2018/08/06	       binh              Initial
 */
package com.binh.source.code.cache;

import org.junit.Assert;
import org.junit.Test;

import com.binh.source.code.cache.api.EhCacheApi;
import com.binh.source.code.cache.api.EhCacheApi.CacheType;
import com.binh.source.code.cache.api.GuavaCacheApi;
import com.binh.source.code.cache.api.MapDBCacheApi;

/**
 * @ClassName @{link HeapCacheTest}
 * @Description TODO
 *
 * @author binh
 * @date 2018/08/06
 */
@SuppressWarnings("unchecked")
public class HeapCacheTest {
    
    @Test
    public void testGuavaHeapCache() {
        GuavaCacheApi.instance().put("k", "v");
        Object value = GuavaCacheApi.instance().get("k");
        Assert.assertNotNull(value);
    }
    
    @Test
    public void testEhcacheHeapCache() {
        EhCacheApi.instance(CacheType.HEAP).put("k", "v");
        Object value = EhCacheApi.instance(CacheType.HEAP).get("k");
        Assert.assertNotNull(value);
    }
    
    @Test
    public void testMapDBHeapCache() {
        MapDBCacheApi.instance(com.binh.source.code.cache.api.MapDBCacheApi.CacheType.HEAP).put("k", "v");
        Object value = MapDBCacheApi.instance(com.binh.source.code.cache.api.MapDBCacheApi.CacheType.HEAP).get("k");
        Assert.assertNotNull(value);
    }
    
    @Test
    public void testEhcacheOutHeapCache() {
        EhCacheApi.instance(CacheType.OUT_HEAP).put("k", "v");
        Object value = EhCacheApi.instance(CacheType.OUT_HEAP).get("k");
        Assert.assertNotNull(value);
    }
    
    @Test
    public void testMapDBOutHeapCache() {
        MapDBCacheApi.instance(com.binh.source.code.cache.api.MapDBCacheApi.CacheType.OUT_HEAP).refresh();
        MapDBCacheApi.instance(com.binh.source.code.cache.api.MapDBCacheApi.CacheType.OUT_HEAP).put("k", "v");
        Object value = MapDBCacheApi.instance(com.binh.source.code.cache.api.MapDBCacheApi.CacheType.OUT_HEAP).get("k");
        Assert.assertNotNull(value);
    }
    
    @Test
    public void testEhcacheDiskCache() {
        EhCacheApi api = EhCacheApi.instance().withCacheType(CacheType.DISK).withRefresh()
            .withCacheFilePath("F:\\cache").init();
        
        api.put("k", "v");
        Object value = api.get("k");
        
        api.close();
        
        Assert.assertNotNull(value);
        
    }
    
    @Test
    public void testMapDBDiskCache() {
        MapDBCacheApi.instance().refresh();
        
        MapDBCacheApi api = MapDBCacheApi.instance();
        
        api.setCacheType(com.binh.source.code.cache.api.MapDBCacheApi.CacheType.DISK);
        api.setCacheFilePath("F:\\dcache");
        api.init();
        
        api.put("k", "v");
        Object value = MapDBCacheApi.instance(com.binh.source.code.cache.api.MapDBCacheApi.CacheType.DISK).get("k");
        
        Assert.assertNotNull(value);
        
        MapDBCacheApi.instance(com.binh.source.code.cache.api.MapDBCacheApi.CacheType.DISK).commit();
        
        System.out.println(value);
    }
}
