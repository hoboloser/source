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

import com.binh.source.code.domain.People;
import com.binh.source.code.mybatis.PeopleSerivce;

/**
 * @ClassName @{link PeopleStaticProxy}
 * @Description 静态代理
 *
 * @author binh
 * @date 2018/07/26
 */
public class PeopleStaticProxy implements PeopleSerivce {

    private PeopleSerivce target;
    
    public PeopleStaticProxy(PeopleSerivce target) {
        this.target = target;
    }
    
    @Override
    public int insert(People people) {
        System.out.println("----begin------");
        target.insert(people);
        System.out.println("----end------");
         return 0;
    }

    @Override
    public int update(People people) {
        // TODO Auto-generated method stub
         return 0;
    }

    @Override
    public int insertMapper(People people) {
        // TODO Auto-generated method stub
         return 0;
    }

    @Override
    public int updateMapper(People people) {
        // TODO Auto-generated method stub
         return 0;
    }

    @Override
    public List<People> listPeoples() {
        // TODO Auto-generated method stub
         return null;
    }

    @Override
    public List<People> listPeoplesCache() {
        // TODO Auto-generated method stub
         return null;
    }

    @Override
    public List<People> listPeoplesSecondCache() {
        // TODO Auto-generated method stub
         return null;
    }

    @Override
    public List<People> listPeoplesFromMapper() {
        // TODO Auto-generated method stub
         return null;
    }

    @Override
    public People getPeopleById(Long id) {
        // TODO Auto-generated method stub
         return null;
    }
}
