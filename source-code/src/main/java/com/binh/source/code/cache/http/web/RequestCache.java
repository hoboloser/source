/**
 * Copyright (c) 
 * 
 * Revision History
 *
 * Date            Programmer              Notes
 * ---------    ---------------------  --------------------------------------------
 * 2018/08/05	       binh              Initial
 */
package com.binh.source.code.cache.http.web;

import com.binh.source.code.cache.http.support.DBService;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixCommandProperties.ExecutionIsolationStrategy;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

/**
 * @ClassName @{link RequestCache}
 * @Description 请求缓存
 *
 *  Hystrix使用了ThreadLocal HystrixRequestContext实现，并在异步线程执行之前注入ThreadLocal HystrixRequestContext
 *  实现多个线程共享，从而实现请求级别的响应缓存。
 *  
 * @author binh
 * @date 2018/08/05
 */
public class RequestCache extends HystrixCommand<String>{
    
    private Long id;
    
    protected RequestCache(Long id) {
        super(RequestCache.setter());
        this.id = id;
    }

    @Override
    protected String run() throws Exception {
        
         return DBService.getData(id);
    }
    
    @Override
    protected String getCacheKey() {
        
         return "request-cache-" + id;
    }
    
    public static Setter setter() {
        
        HystrixCommandProperties.Setter commandProperties = HystrixCommandProperties.Setter()
            .withExecutionIsolationStrategy(ExecutionIsolationStrategy.THREAD)
            //需要使用withRequestCacheEnabled(true)配置开启请求缓存支持
            .withRequestCacheEnabled(true);
        Setter setter = HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("request"));
        
        setter.andCommandPropertiesDefaults(commandProperties);
        
        return setter;
    }
    
    /**
     * 请求缓存
     */
    public static void cache() {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            RequestCache cache1 = new RequestCache(1L);
            RequestCache cache2 = new RequestCache(1L);
            cache1.execute();
            cache2.execute();
            org.springframework.util.Assert.isTrue(!cache1.isResponseFromCache, "no from cache ");
            org.springframework.util.Assert.isTrue(cache2.isResponseFromCache, "from cache");
        } finally {
            context.shutdown();
        }
    }
    
    public static void main(String[] args) {
        try {
            cache();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
