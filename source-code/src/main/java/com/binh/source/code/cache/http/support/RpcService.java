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

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * @ClassName @{link RpcService}
 * @Description RPC依赖服务
 *
 * @author binh
 * @date 2018/08/05
 */
public class RpcService {

    /**
     * RPC模拟调用
     * 10ms
     * @return
     */
    public static Map<String, Object> getRpcResult() {
        
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
             e.printStackTrace();
        }
        
        return Maps.newHashMap();
    }
}
