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

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCollapserKey;
import com.netflix.hystrix.HystrixCollapserProperties;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

/**
 * @ClassName @{link RequestMerge}
 * @Description 请求合并
 *
 * @author binh
 * @date 2018/08/05
 */
public class RequestMerge extends HystrixCollapser<List<String>, String, Long>{
    
    private Long id;
    
    public RequestMerge(Long id) {
        super(RequestMerge.setter());
        this.id = id;
    }
    
    /**
     * 返回请求参数，如果有多个参数需要包装为一个，参数会被封装为CollapsedRequest
     */
    @Override
    public Long getRequestArgument() {
         return id;
    }

    /**
     * 创建可批量执行的Command，当HystrixCollapser对请求进行合并后达到MaxRequestsInBatch时或TimerDelayInMilliseconds
     * 超时后，就会创建批处理命令。
     */
    @Override
    protected HystrixCommand<List<String>> createCommand(Collection<CollapsedRequest<String, Long>> requests) {
        
         return new BatchCommand(requests);
    }

    /**
     * 将执行结果映射到请求中，从而单个请求就可以获得结果了
     */
    @Override
    protected void mapResponseToRequests(List<String> batchResponse,
        Collection<CollapsedRequest<String, Long>> requests) {
        final AtomicInteger count = new AtomicInteger(0);
        requests.forEach((request) -> {
            request.setResponse(batchResponse.get(count.getAndIncrement()));
        });
    }
    
    /**
     * 如果想把不同的请求分到不同的分组进行请求合并，可以使用，如HashTag应用。
     */
    @Override
    protected Collection<Collection<CollapsedRequest<String, Long>>>
        shardRequests(Collection<CollapsedRequest<String, Long>> requests) {
         return super.shardRequests(requests);
    }
    /**
     * withCollapserKey 配置全局唯一标识服务合并的名称，通过该名称合并请求
     * CollapserPropertiesDefaults
     *      MaxRequestsInBatch：配置每个请求合并允许的最大请求数，如果请求多于此配置会分多批次执行，默认Integer.MAX_VALUE
     *      TimerDelayInMilliseconds:配置在批处理执行之前的等待超时时间，默认10s
     *      RequestCacheEnabled:如果跨多请求进行请求合并，则必须开启，开启后可消除重复请求，默认true
     * scope:请求合并范围，Scope.REQUEST 即当前请求上下文，默认值。Scope.GLOBAL表示全局，即可以跨越多个请求上下文进行请求合并。
     * @return
     */
    public static Setter setter() {
        return HystrixCollapser.Setter.withCollapserKey(HystrixCollapserKey.Factory.asKey("batch-cache"))
            .andCollapserPropertiesDefaults(HystrixCollapserProperties.Setter()
                .withMaxRequestsInBatch(3)
                .withTimerDelayInMilliseconds(5)
                .withRequestCacheEnabled(true))
            .andScope(Scope.REQUEST);
    }
    /**
     * 请求合并
     * 
     * Hystrix内部会将多个查询进行合并后批量查询，需要使用queue而不能使用excute方法调用
     * 
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    public static void merge() throws InterruptedException, ExecutionException {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            RequestMerge cache1 = new RequestMerge(1L);
            RequestMerge cache2 = new RequestMerge(2L);
            RequestMerge cache3 = new RequestMerge(3L);
            
            Future<String> f1 = cache1.queue();
            Future<String> f2 = cache2.queue();
            Future<String> f3 = cache3.queue();
            
            System.out.println("f1 -> " + f1.get());
            System.out.println("f2 -> " + f2.get());
            System.out.println("f3 -> " + f3.get());
        } finally {
            context.shutdown();
        }
        
    }
    
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        merge();
    }

    
}
