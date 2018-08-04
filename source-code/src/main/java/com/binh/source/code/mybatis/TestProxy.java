/**
 * Copyright (c) 
 * 
 * Revision History
 *
 * Date            Programmer              Notes
 * ---------    ---------------------  --------------------------------------------
 * 2018/07/26	       binh              Initial
 */
package com.binh.source.code.mybatis;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import sun.misc.ProxyGenerator;

/**
 * @ClassName @{link TestProxy}
 * @Description TODO
 *
 * @author binh
 * @date 2018/07/26
 */
public class TestProxy<T> {
    
    private T target;
    
    public TestProxy() {}
    
    public TestProxy(T target) {
        
        this.target = target;
    }
    
    @SuppressWarnings("unchecked")
    public T getProxy() {
        return (T)Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new InvocationHandler() {
            
            @Override
            public Object invoke(Object arg0, Method arg1, Object[] arg2) throws Throwable {
                
                return arg1.invoke(target, arg2);
            }
        });
    }
    
    public static void writeClassToDisk(String path, Class clazz){
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        byte[] classFile = ProxyGenerator.generateProxyClass("com.sun.proxy.$Proxy0", new Class[]{clazz});
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            fos.write(classFile);
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        TestMoService s = new TestMoServiceImpl();
        TestProxy<TestMoService> proxy = new TestProxy<TestMoService>(s);
        TestMoService ser = proxy.getProxy();
        writeClassToDisk("D:\\$Proxy0.class", PeopleSerivce.class);
        ser.print();
        
    }
}
