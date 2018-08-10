/**
 * Copyright (c) 
 * 
 * Revision History
 *
 * Date            Programmer              Notes
 * ---------    ---------------------  --------------------------------------------
 * 2018/08/06	       binh              Initial
 */
package com.binh.source.code.controller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.binh.source.code.cache.http.support.DBService;
import com.binh.source.code.cache.http.web.AsyncAnnotation;
import com.binh.source.code.cache.http.web.AsyncWeb;

/**
 * @ClassName @{link HttpController}
 * @Description 模拟测试
 *
 * @author binh
 * @date 2018/08/06
 */
@Controller
@RequestMapping()
@WebServlet(asyncSupported=true)
public class HttpController extends BaseController {
    
    @RequestMapping("/async")
    public void asyncWeb(String name, HttpServletRequest request, HttpServletResponse response) {
        logger.info("asyncWeb");
        AsyncWeb asyncWeb = new AsyncWeb();
        try {
            asyncWeb.submit(request, () -> {
                CompletableFuture future = new CompletableFuture<>();
                future.complete(DBService.getData(name));
                return future;
            });
        } catch (Exception e) {
             e.printStackTrace();
        }
    }
    
    
    @RequestMapping("/asynco")
    @AsyncAnnotation
    @ResponseBody
    public String asyncWeb0(String name, HttpServletRequest request, HttpServletResponse response) {
        logger.info("asyncWeb");
        return DBService.getData(name); 
    }
}
