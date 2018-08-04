/**
 * Copyright (c) 
 * 
 * Revision History
 *
 * Date            Programmer              Notes
 * ---------    ---------------------  --------------------------------------------
 * 2018/07/24	       binh              Initial
 */
package com.binh.source.code.domain;

import java.io.Serializable;

/**
 * @ClassName @{link People}
 * @Description TODO
 *
 * @author binh
 * @date 2018/07/24
 */
public class People implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private long id;
    
    private String name;
    
    private int age;
    
    public People() {
    }

    public People(long id) {
        this(id, null, 0);
    }
    
    public People(String name,int age) {
        this(0, name, age);
    }
    
    public People(long id,String name,int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "People [id=" + id + ", name=" + name + ", age=" + age + "]";
    }
    
}
