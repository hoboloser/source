/**
 * Copyright (c) 
 * 
 * Revision History
 *
 * Date            Programmer              Notes
 * ---------    ---------------------  --------------------------------------------
 * 2018/08/05	       binh              Initial
 */
package com.binh.source.code.cache.http;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.binh.source.code.cache.http.support.HttpService;
import com.binh.source.code.cache.http.support.RpcService;

/**
 * @ClassName @{link AsyncFuture}
 * @Description 异步Future调用
 *
 *  线程池配合Future使用，但是阻塞主请求进程，高并发时依然会造成线程数过多、CPU上下文切换。
 * @author binh
 * @date 2018/08/05
 */
public class AsyncFuture {

    public static final ExecutorService executor = Executors.newFixedThreadPool(2);
    
    public static void async() {
        Future rpcFuture = null;
        Future httpFuture = null;
        
        try {
            rpcFuture = executor.submit(() -> {
                RpcService.getRpcResult();
            });
            
            httpFuture = executor.submit(() -> {
                
                HttpService.getHttpResult();
            });
            
            Map<String, Object> rpcResult = (Map<String, Object>)rpcFuture.get(300, TimeUnit.MILLISECONDS);
            
            Object httpResult = httpFuture.get(300, TimeUnit.MILLISECONDS);
             
        } catch (Exception e) {
            e.printStackTrace();
            
            if (rpcFuture != null) {
                rpcFuture.cancel(true);
            }
            if (httpFuture != null) {
                httpFuture.cancel(true);
            }
            throw new RuntimeException(e);
        }
    }
    
    public static void main(String[] args) {
        long begin = System.currentTimeMillis();
        
        async();    
        
        long end = System.currentTimeMillis();
        
        System.out.println("异步future调用耗时： " + (end - begin) + " ms");
    }
}
