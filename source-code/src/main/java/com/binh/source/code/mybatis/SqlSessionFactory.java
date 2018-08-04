package com.binh.source.code.mybatis;

import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.TransactionIsolationLevel;

public interface SqlSessionFactory {

    /**
     * 以默认非自动提交形式开启SqlSession
     * 默认配置：
     * ExecutorType defaultExecutorType = ExecutorType.SIMPLE;
     * autoCommit = false
     * TransactionIsolationLevel = null
     * @return
     */
    SqlSession openSession();
    /**
     * 以autoCommit定义的形式开启SqlSession
     * autoCommit=true   为自动提交事务
     * autoCommit=false  为非自动提交事务
     * @return
     */
    SqlSession openSession(boolean autoCommit);
    
    /**
     * 通过Connection配置开启SqlSession
     * @param connection
     * @return
     */
    SqlSession openSession(Connection connection);
    
    /**
     * 通过配置TransactionIsolationLevel事务级别来开启SqlSession
     * @param level
     * @return
     */
    SqlSession openSession(TransactionIsolationLevel level);
    /**
     * 通过配置ExecutorType执行器来开启SqlSession
     * ExecutorType.SIMPLE
     *      这个类型不做特殊的事情，它只为每个语句创建一个PreparedStatement。
     * ExecutorType.REUSE
     *      这种类型将重复使用PreparedStatements。
     * ExecutorType.BATCH
     *      这个类型批量更新，且必要地区别开其中的select 语句，确保动作易于理解。
     * 默认执行器是SIMPLE。
     * @param level
     * @return
     */
    SqlSession openSession(ExecutorType execType);

    SqlSession openSession(ExecutorType execType, boolean autoCommit);

    SqlSession openSession(ExecutorType execType, TransactionIsolationLevel level);

    SqlSession openSession(ExecutorType execType, Connection connection);

    Configuration getConfiguration();
    
}