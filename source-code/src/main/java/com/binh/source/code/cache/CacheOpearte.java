/**
 * Copyright (c) 
 * 
 * Revision History
 *
 * Date            Programmer              Notes
 * ---------    ---------------------  --------------------------------------------
 * 2018/08/01	       binh              Initial
 */
package com.binh.source.code.cache;

/**
 * @ClassName @{link CacheOpearte}
 * @Description 缓存操作
 *
 * @author binh
 * @date 2018/08/01
 */
public class CacheOpearte {
    
    
    @SuppressWarnings("static-access")
    public static void main(String[] args) throws InterruptedException {
        System.out.println("1");
        
        Thread.currentThread().sleep(1000);
        //当JVM停止或者重启的时候，执行的代码
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            
            try {
                Thread.currentThread().sleep(2000);
            } catch (InterruptedException e) {
                 e.printStackTrace();
            }
            System.out.println("1111111111111");
            
        }));
        Thread.currentThread().sleep(1000);
        
        System.out.println("2");
    }
}
