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

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.nio.client.HttpAsyncClient;
import org.apache.http.nio.client.methods.HttpAsyncMethods;
import org.apache.http.nio.protocol.BasicAsyncResponseConsumer;
import org.apache.http.nio.protocol.HttpAsyncRequestProducer;

/**
 * @ClassName @{link AsyncCallback}
 * @Description 异步Callback
 *  
 *  通过回调机制实现，即首先发出网络请求，当网络返回时回调相关方法，如HttpAsyncClient使用基于NIO的异步I/O模型实现，
 *  它实现了Reactor模式，摒弃阻塞I/O模型one thread per connection，采用线程池分发时间通知，从而有效支撑大量并发链接。
 *  
 *  这种机制并不能提升性能，而是为了支撑大量并发连接或者提升吞吐量。
 * @author binh
 * @date 2018/08/05
 */
public class AsyncCallback {
    
    private static CloseableHttpAsyncClient httpAsyncClient = HttpAsyncClientBuilder.create().build() ;

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
            
        httpAsyncClient.start();
        httpAsyncClient.execute(producer, consumer, callback);
            
        return future;
    }
    
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        long begin = System.currentTimeMillis();
        
        CompletableFuture<Object> future = async("http://www.baidu.com");
        
        Object result = future.get();
        
        long end = System.currentTimeMillis();
        
        System.out.println("异步callback调用耗时： " + (end - begin) + " ms");
        
        System.out.println(result);
    }
}
