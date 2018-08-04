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

/**
 * @ClassName @{link TestMoService}
 * @Description 用于测试Proxy生成代理类时定义的package，默认public的位com.sun.proxy.$Proxy0.class,
 * 其他的修饰符则是指定类的全限定包名com.binh.source.code.mybatis.$Proxy0.class
 * 
 * @author binh
 * @date 2018/07/26
 */
interface TestMoService {

    void print();
}
