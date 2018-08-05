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

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.nio.client.methods.HttpAsyncMethods;
import org.apache.http.nio.protocol.BasicAsyncResponseConsumer;
import org.apache.http.nio.protocol.HttpAsyncRequestProducer;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

/**
 * @ClassName @{link AsyncArrange}
 * @Description 异步编排
 * 
 * JDK8提供了新的异步编排思想，可以对多个异步处理进行编排，实现更复杂的异步处理。
 * 其内部使用 {@link ForkJoinPool} 实现异步处理。
 * 使用 {@link CompletableFuture} 可以把回调方式的实现转变为同步调用实现。
 *
 *
 * @author binh
 * @date 2018/08/05
 */
public class AsyncArrange {

    private static CloseableHttpAsyncClient httpAsyncClient = HttpAsyncClientBuilder.create().build() ;
    
    static {
        httpAsyncClient.start();
    }

    public static CompletableFuture<Object> async(String url) {
        
        CompletableFuture future = new CompletableFuture<>();
        
        HttpAsyncRequestProducer producer = HttpAsyncMethods.create(new HttpPost(url));
        
        BasicAsyncResponseConsumer consumer = new BasicAsyncResponseConsumer();
        
        FutureCallback callback = new FutureCallback<HttpResponse>() {

            @Override
            public void cancelled() {
                future.cancel(true);
            }

            @Override
            public void failed(Exception arg0) {
                 future.completeExceptionally(arg0);
            }

            @Override
            public void completed(HttpResponse arg0) {
                 future.complete(arg0);
            }
        };
            
        httpAsyncClient.execute(producer, consumer, callback);
            
        return future;
    }
    
    /**
     * 场景一：三个服务异步并发调用，然后对结果合并处理，不阻塞主线程
     */
    public static void async() {
        CompletableFuture<Object> future1 = async("http://www.baidu.com");
        
        CompletableFuture<Object> future2 = async("http://www.baidu.com");
        
        CompletableFuture<Object> future3 = async("http://www.baidu.com");
        
        CompletableFuture.allOf(future1, future2, future3).thenAcceptAsync((Void) -> {
            // 异步处理结果
            try {
                Object result1= future1.get();
                Object result2= future2.get();
                Object result3= future3.get();
                
                System.out.println("result1 -> " + result1);
                System.out.println("result2 -> " + result2);
                System.out.println("result3 -> " + result3);
            } catch (Exception e) {
                
            }
        }).exceptionally(t -> {
            return null;
        });
    }
    
    
    /**
     * 场景一：三个服务异步并发调用，通过返回一个新的CompletableFuture来同步处理结果，即阻塞主线程。
     */
    public static CompletableFuture<ArrayList<Object>> async1() {
        CompletableFuture<Object> future1 = async("http://www.baidu.com");
        
        CompletableFuture<Object> future2 = async("http://www.baidu.com");
        
        CompletableFuture<Object> future3 = async("http://www.baidu.com");
        
        CompletableFuture<ArrayList<Object>> future4 = CompletableFuture.allOf(future1, future2, future3).thenApply((Void) -> {
            try {
                return Lists.newArrayList(future1.get(), future2.get(), future3.get());
            } catch (InterruptedException e) {
                 e.printStackTrace();
            } catch (ExecutionException e) {
                 e.printStackTrace();
            }
            return null;
        }).exceptionally(t -> {
            return null;
        });
        
        return future4;
    }
    
    /**
     * 场景二、两个服务并发调用，然后消费结果，不阻塞主线程
     */
    public static void async2() {
        CompletableFuture<Object> future1 = async("http://www.baidu.com");
        
        CompletableFuture<Object> future2 = async("http://www.baidu.com");
//        
//        future1.thenAcceptBothAsync(future2, (future1.get(), future2.get()) -> {
//            
//            
//        }).exceptionally(t -> {
//            return null;
//        });
    }
    
    /**
     * 场景三：服务1执行完成后，接着并发执行服务2和服务3，然后消费相关结果，不阻塞主线程
     */
    public static void async3() {
        CompletableFuture<Object> future1 = async("http://www.baidu.com");
        
        CompletableFuture<Object> future2 = future1.thenApplyAsync((v) -> {
            return async("http://www.baidu.com");
        });
        
        CompletableFuture<Object> future3 = async("http://www.baidu.com");
//        
//        future2.thenCombineAsync(future3, (future2.get(), future3.get()) -> {
//            
//            return null;
//        });
        
    }
    
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        long begin = System.currentTimeMillis();
        
        async();
        
        CompletableFuture<ArrayList<Object>> result1 = async1();
        
        System.out.println("async() 主线程继续执行.......");
        
        System.out.println("result1 主线程阻塞 -> " + JSON.toJSONString(result1.get()));
        
        System.out.println("async1() 主线程阻塞.......");
        
        long end = System.currentTimeMillis();
        
        System.out.println("异步future调用耗时： " + (end - begin) + " ms");
    }
}
