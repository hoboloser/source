/**
 * Copyright (c) 
 * 
 * Revision History
 *
 * Date            Programmer              Notes
 * ---------    ---------------------  --------------------------------------------
 * 2018/07/24	       binh              Initial
 */
package com.binh.source.code.mybatis.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.binh.source.code.dao.mapper.PeopleDaoMapper;
import com.binh.source.code.domain.People;
import com.binh.source.code.mybatis.PeopleSerivce;

/**
 * @ClassName @{link PeopleServiceImpl}
 * @Description TODO
 *
 * @author binh
 * @date 2018/07/24
 */
public class PeopleServiceImpl implements PeopleSerivce {
    
    private static Logger logger = LoggerFactory.getLogger(PeopleServiceImpl.class);

    private SqlSessionFactory sqlSessionFactory = null;
    
    {
        getFactory();
    }
    public int insert(People people) {
        SqlSession sqlSession = openSession();
        try {
            return sqlSession.insert("com.binh.source.code.domain.People.insert", people);
        } finally {
            sqlSession.close();
        }
    }
    
    public int insertMapper(People people) {
        SqlSession sqlSession = openSession();
        try {
            PeopleDaoMapper mapper = sqlSession.getMapper(PeopleDaoMapper.class);
            return mapper.insert(people);
        } finally {
            sqlSession.close();
        }
   }

   public int updateMapper(People people) {
       SqlSession sqlSession = openSession();
       try {
           PeopleDaoMapper mapper = sqlSession.getMapper(PeopleDaoMapper.class);
           return mapper.update(people);
       } finally {
           sqlSession.close();
       }
   }
   

    public int update(People people) {
        SqlSession sqlSession = openSession();
        try {
            return sqlSession.insert("com.binh.source.code.domain.People.update", people);
        } finally {
            sqlSession.close();
        }
    }

    public List<People> listPeoples() {
        SqlSession sqlSession = openSession();
        try {
            List<People> list = sqlSession.selectList("com.binh.source.code.domain.People.listPeople");
            return list;
        } finally {
            sqlSession.close();
        }
    }
    
    public List<People> listPeoplesCache() {
        
        SqlSession sqlSession = openSession();
        try {
            List<People> list1 = sqlSession.selectList("com.binh.source.code.domain.People.listPeople");
            System.out.println("list1 : " + JSON.toJSONString(list1));
            insert(new People("xxx",1));
            
            List<People> list2 = sqlSession.selectList("com.binh.source.code.domain.People.listPeople");
            System.out.println("list2 : " + JSON.toJSONString(list2));
            return list2;
        } finally {
            sqlSession.close();
        }
   }
    

    public List<People> listPeoplesSecondCache() {
        List<People> list1 = listPeoplesFromMapper();
        System.out.println("list1 : " + JSON.toJSONString(list1));
        insert(new People("xxx",1));
        
        List<People> list2 = listPeoplesFromMapper();
        System.out.println("list2 : " + JSON.toJSONString(list2));
        return list2;
    }

    public People getPeopleById(Long id) {
        SqlSession sqlSession = openSession();
        try {
            PeopleDaoMapper mapper = sqlSession.getMapper(PeopleDaoMapper.class);
            return mapper.getPeopleById(id);
        } finally {
            sqlSession.close();
        }
    }

    public List<People> listPeoplesFromMapper() {
        SqlSession sqlSession = openSession();
        try {
            PeopleDaoMapper mapper = sqlSession.getMapper(PeopleDaoMapper.class);
            return mapper.listPeoples();
        } finally {
            sqlSession.close();
        }
    }
    
    private SqlSessionFactory getFactory() {
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        
        try {
            inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            logger.error("get sql session occur an exception: {}", e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error("close inputStream occur an exception: {}", e.getMessage());
                }
            }
        }
        
        return sqlSessionFactory;
    }
    
    private SqlSession openSession() {
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        return sqlSession;
    }

}
