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

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

/**
 * @ClassName @{link AsyncWeb}
 * @Description 异步Web服务实现
 *
 *  借助Servlet3、CompletableFuture实现异步Web服务。
 *  Servlet容器接受到请求之后，Tomcat需要先解析请求体，然后通过异步Servlet将请求交给异步线程池来完成业务处理，
 *  Tomcat线程释放回容器。
 *  通过异步机制可以提升Tomcat容器的吞吐量
 *  
 * @author binh
 * @date 2018/08/05
 */
@Component("asyncWeb")
public class AsyncWeb {
    
    private Logger logger = LoggerFactory.getLogger(AsyncWeb.class);

    private static final long asyncTimeoutInSeconds = 3;
    
    private AsyncListener asyncListener = null;
    
    private ExecutorService excutor = Executors.newWorkStealingPool();
    
    public void process(final HttpServletRequest req, final Callable<CompletableFuture> task) {
        logger.info("AsyncWeb.submit");
        req.setAttribute("org.apache.catalina.ASYNC_SUPPORTED", true);
        final String uri = req.getRequestURI();
        final Map params = req.getParameterMap();
        final AsyncContext asyncContext = req.startAsync();
        asyncContext.getRequest().setAttribute("uri", uri);
        asyncContext.getRequest().setAttribute("params", params);
        asyncContext.setTimeout(asyncTimeoutInSeconds * 1000);
        
        if (asyncListener != null) {
            asyncContext.addListener(asyncListener);
        }
        excutor.execute(() -> {
            CompletableFuture future = null;
            try {
                future = task.call();
            } catch (Exception e1) {
                 e1.printStackTrace();
            }
        });
    }
    
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void submit(final HttpServletRequest req, final Callable<CompletableFuture> task) throws Exception {
        logger.info("AsyncWeb.submit");
        req.setAttribute("org.apache.catalina.ASYNC_SUPPORTED", true);
        final String uri = req.getRequestURI();
        final Map params = req.getParameterMap();
        final AsyncContext asyncContext = req.startAsync();
        asyncContext.getRequest().setAttribute("uri", uri);
        asyncContext.getRequest().setAttribute("params", params);
        asyncContext.setTimeout(asyncTimeoutInSeconds * 1000);
        
        if (asyncListener != null) {
            asyncContext.addListener(asyncListener);
        }
        excutor.execute(() -> {
            CompletableFuture future = null;
            try {
                future = task.call();
            } catch (Exception e1) {
                 e1.printStackTrace();
            }
            
            future.thenAccept(t -> {
                HttpServletResponse resp = (HttpServletResponse) asyncContext.getResponse();
                try {
                    if (t instanceof String) {
                        byte[] bytes = ((String)t).getBytes();
                        resp.setContentType("text/html;charset=gbk");
                        resp.setContentLength(bytes.length);
                        resp.getOutputStream().write(bytes);
                    } else {
                        resp.getOutputStream().write(JSON.toJSONString(t).getBytes());
                    }
                } catch (Exception e) {
                    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    try {
                        logger.error("get info error, uri:{},params:{}", uri, params);
                    } catch (Exception ex) {
                        
                    }
                } finally {
                    asyncContext.complete();
                }
            }).exceptionally(e -> {
                asyncContext.complete();
                return null;
            });
        });
    }
}
