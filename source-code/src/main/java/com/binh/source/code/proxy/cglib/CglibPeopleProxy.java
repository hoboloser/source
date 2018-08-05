/**
 * Copyright (c) 
 * 
 * Revision History
 *
 * Date            Programmer              Notes
 * ---------    ---------------------  --------------------------------------------
 * 2018/07/28	       binh              Initial
 */
package com.binh.source.code.proxy.cglib;

import java.lang.reflect.Method;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.LockSupport;

import com.alibaba.fastjson.JSON;
import com.binh.source.code.mybatis.impl.PeopleServiceImpl;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * @ClassName @{link CglibPeopleProxy}
 * @Description TODO
 *
 * @author binh
 * @date 2018/07/28
 */
public class CglibPeopleProxy {

    public static void main(String[] args) {
        
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(PeopleServiceImpl.class);
        enhancer.setCallback(new MethodInterceptor() {
            
            @Override
            public Object intercept(Object arg0, Method arg1, Object[] arg2, MethodProxy arg3) throws Throwable {
                return arg3.invokeSuper(arg0, arg2);
            }
        });
        
        PeopleServiceImpl impl = (PeopleServiceImpl)enhancer.create();
        System.out.println(JSON.toJSONString(impl.listPeoples()));
        
        LockSupport.park();
    }
}
