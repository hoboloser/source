/**
 * Copyright (c) 
 * 
 * Revision History
 *
 * Date            Programmer              Notes
 * ---------    ---------------------  --------------------------------------------
 * 2018/07/24	       binh              Initial
 */
package com.binh.source.code.mybatis;

import java.util.List;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.binh.source.code.domain.People;
import com.binh.source.code.mybatis.impl.PeopleServiceImpl;

/**
 * @ClassName @{link PeopleServiceTest}
 * @Description TODO
 *
 * @author binh
 * @date 2018/07/24
 */
public class PeopleServiceTest {
    
    @Test
    public void testListPeoplesCache() {
        PeopleSerivce peopleSerivce = new PeopleServiceImpl();
        List<People> list = peopleSerivce.listPeoplesCache();
        System.out.println(JSON.toJSONString(list));
    }
    
    @Test
    public void testListPeoplesSecondCache() {
        PeopleSerivce peopleSerivce = new PeopleServiceImpl();
        List<People> list = peopleSerivce.listPeoplesSecondCache();
        System.out.println(JSON.toJSONString(list));
    }
    
    @Test
    public void testListPeoples() {
        PeopleSerivce peopleSerivce = new PeopleServiceImpl();
        List<People> list = peopleSerivce.listPeoples();
        System.out.println(JSON.toJSONString(list));
    }
    
    @Test
    public void testListPeoplesMapper() {
        PeopleSerivce peopleSerivce = new PeopleServiceImpl();
        List<People> list = peopleSerivce.listPeoplesFromMapper();
        System.out.println(JSON.toJSONString(list));
    }
    
    @Test
    public void testInsertPeoples() {
        PeopleSerivce peopleSerivce = new PeopleServiceImpl();
        int result = peopleSerivce.insert(new People("name", 13));
        System.out.println(result);
    }
    
    @Test
    public void testInsertPeoplesMapper() {
        PeopleSerivce peopleSerivce = new PeopleServiceImpl();
        int result = peopleSerivce.insertMapper(new People("name", 14));
        System.out.println(result);
    }
    
    @Test
    public void testUpdatePeoples() {
        PeopleSerivce peopleSerivce = new PeopleServiceImpl();
        int result = peopleSerivce.updateMapper(new People(2, "name2", 15));
        System.out.println(result);
    }
    
    @Test
    public void testUpdatePeoplesMapper() {
        PeopleSerivce peopleSerivce = new PeopleServiceImpl();
        int result = peopleSerivce.update(new People(1, "name1", 16));
        System.out.println(result);
    }
    
}
