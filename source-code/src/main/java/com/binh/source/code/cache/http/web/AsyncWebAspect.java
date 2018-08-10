/**
 * Copyright (c)
 * 
 * Revision History
 *
 * Date Programmer Notes --------- --------------------- -------------------------------------------- 2018/08/07 binh
 * Initial
 */
package com.binh.source.code.cache.http.web;

import java.util.concurrent.CompletableFuture;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.binh.source.code.controller.BaseController;

/**
 * @ClassName @{link AsyncWebAspect}
 * @Description TODO
 *
 * @author binh
 * @date 2018/08/07
 */
@Component
@Aspect
public class AsyncWebAspect extends BaseController {

    @Autowired
    private AsyncWeb asyncWeb;

    @Pointcut("@annotation(com.binh.source.code.cache.http.web.AsyncAnnotation)")
    public void pointCut() {}

    @Around("pointCut()")
    public void around(ProceedingJoinPoint point) {
        System.out.println("async web aop begin");
        logger.info("async web aop begin");
        Object[] args = point.getArgs();
        HttpServletRequest request = null;
        HttpServletResponse response = null;
        for (Object object : args) {
            if (object instanceof HttpServletRequest) {
                request = (HttpServletRequest) object;
            } else if (object instanceof HttpServletResponse) {
                response = (HttpServletResponse) object; 
            }
        }
        try {
            if (request == null) {
                point.proceed();
            } else {
                asyncWeb.submit(request, () -> {
                    CompletableFuture future = new CompletableFuture<>();
                    try {
                        future.complete(point.proceed());
                    } catch (Throwable e) {
                        logger.error("async web aop future occur an error, {}", e.getMessage());
                    }
                    return future;
                });
            }
        } catch (Throwable e) {
            logger.error("async web aop occur an error, {}", e.getMessage());
            if (response != null) {
                writeAjaxJSONResponse("系统维护中", response);
            }
        } finally {
            logger.info("async web aop finally end");
        }
    }
}
