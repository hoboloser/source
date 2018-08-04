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
import java.lang.reflect.Method;

/**
 * @ClassName @{link ProxyInvokehandler}
 * @Description TODO
 *
 * @author binh
 * @date 2018/07/26
 */
public class ProxyInvokehandler implements InvocationHandler {
    
    private Object target;
    
    public ProxyInvokehandler() {}
    
    public ProxyInvokehandler(Object target) {
        this.target = target;
    }
    
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(target, args);
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

}
