/**
 * Copyright (c) 
 * 
 * Revision History
 *
 * Date            Programmer              Notes
 * ---------    ---------------------  --------------------------------------------
 * 2018/08/05	       binh              Initial
 */
package com.binh.source.code.cache.http.support;

/**
 * @ClassName @{link HttpService}
 * @Description HTTP依赖服务
 *
 * @author binh
 * @date 2018/08/05
 */
public class HttpService {

    public static Object getHttpResult() {
        
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
             e.printStackTrace();
        }
        
        return new Object();
    }
}
