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

import java.util.List;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.binh.source.code.mybatis.PeopleSerivce;
import com.binh.source.code.mybatis.impl.PeopleServiceImpl;

/**
 * @ClassName @{link JDKProxyTest}
 * @Description TODO
 *
 * @author binh
 * @date 2018/07/26
 */
public class JDKProxyTest {

    @Test
    public void test() {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        PeopleSerivce service1 = new PeopleServiceImpl();
        PeopleProxy proxy = new PeopleProxy();
        PeopleSerivce service = proxy.proxy(service1);
        List list = service.listPeoples();
        System.out.println(JSON.toJSONString(list));
    }
}
