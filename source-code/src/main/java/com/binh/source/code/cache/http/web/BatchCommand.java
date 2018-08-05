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
import java.util.stream.Collectors;

import com.binh.source.code.cache.http.support.DBService;
import com.netflix.hystrix.HystrixCollapser.CollapsedRequest;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixCommand.Setter;
import com.netflix.hystrix.HystrixCommandProperties.ExecutionIsolationStrategy;

/**
 * @ClassName @{link BatchCommand}
 * @Description 请求合并批处理
 *
 * @author binh
 * @date 2018/08/05
 */
public class BatchCommand extends HystrixCommand<List<String>> {
    
    private Collection<CollapsedRequest<String, Long>> requests;
    
    protected BatchCommand(Collection<CollapsedRequest<String, Long>> requests) {
        super(BatchCommand.setter());
        this.requests = requests;
    }

    
    @Override
    protected List<String> run() throws Exception {
        List<Long> ids = requests.stream().map(req -> {return req.getArgument();}).collect(Collectors.toList());
        return DBService.getData(ids);
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
}
