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

import com.binh.source.code.cache.http.support.HttpService;
import com.binh.source.code.cache.http.support.RpcService;

/**
 * @ClassName @{link SyncTransfer}
 * @Description 同步阻塞调用
 *
 * @author binh
 * @date 2018/08/05
 */
public class SyncTransfer {

    public static void sync() {
        
        Map<String, Object> rpcResult = RpcService.getRpcResult();
        
        Object httpResult = HttpService.getHttpResult();
        
    }
    
    public static void main(String[] args) {
        long begin = System.currentTimeMillis();
        
        sync();    
        
        long end = System.currentTimeMillis();
        
        System.out.println("同步阻塞调用耗时： " + (end - begin) + " ms");
    }
}
