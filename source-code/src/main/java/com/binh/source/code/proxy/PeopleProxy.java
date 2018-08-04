/**
 * Copyright (c) 
 * 
 * Revision History
 *
 * Date            Programmer              Notes
 * ---------    ---------------------  --------------------------------------------
 * 2018/07/26	       binh              Initial
 */
package com.binh.source.code.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import com.binh.source.code.mybatis.PeopleSerivce;

/**
 * @ClassName @{link PeopleProxy}
 * @Description 动态代理
 *
 * @author binh
 * @date 2018/07/26
 */
public class PeopleProxy {
    
    public <T> T proxy(Object obj) {
        InvocationHandler h = new ProxyInvokehandler(obj);
        return (T)Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), h);
    }
    
    
}
