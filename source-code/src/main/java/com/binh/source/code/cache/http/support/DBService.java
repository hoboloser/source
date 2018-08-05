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

import java.util.List;

import com.google.common.collect.Lists;

/**
 * @ClassName @{link DBService}
 * @Description DB依赖服务
 *
 * @author binh
 * @date 2018/08/05
 */
public class DBService {

    
    public static String getData(Long id) {
        
        return "db data " + id;
    }
    
    public static List<String> getData(List<Long> ids) {
        
        return Lists.newArrayList("1","2","3");
    }
}
