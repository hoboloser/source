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

import com.binh.source.code.domain.People;

/**
 * @ClassName @{link PeopleSerivce}
 * @Description TODO
 *
 * @author binh
 * @date 2018/07/24
 */
public interface PeopleSerivce {
    
    public int insert(People people);
    
    public int update(People people);
    
    public int insertMapper(People people);
    
    public int updateMapper(People people);
    
    public List<People> listPeoples();
    
    /** 
     * 缓存测试注意点：
     * SqlSessionFactory 必须是同一个，也就是作用域需要为应用作用域
     * */
    /**
     * 测试mybatis一级缓存
     * 使用同一个SqlSession即认为开启一级缓存
     * SqlSession层面缓存
     * MyBatis的一级缓存指的是在一个Session域内,session未关闭的时候执行的查询会根据SQL为key被缓存(跟mysql缓存一样,修改任何参数的值都会导致缓存失效)
     * @return
     */
    public List<People> listPeoplesCache();
    
    /**
     * 测试mybatis二级缓存
     * 二级缓存的对象需要实现序列化接口，Serializable
     * <setting name="cacheEnabled" value="true"/>
     * <cache eviction="FIFO" flushInterval="60000" size="512" readOnly="false"/>
     * @return
     */
    public List<People> listPeoplesSecondCache();
    
    public List<People> listPeoplesFromMapper();
    
    public People getPeopleById(Long id);
}
