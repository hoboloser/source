/**
 * Copyright (c) 
 * 
 * Revision History
 *
 * Date            Programmer              Notes
 * ---------    ---------------------  --------------------------------------------
 * 2018/07/24	       binh              Initial
 */
package com.binh.source.code.dao.mapper;

import java.util.List;

import com.binh.source.code.domain.People;

/**
 * @ClassName @{link PeopleDaoMapper}
 * @Description TODO
 *
 * @author binh
 * @date 2018/07/24
 */
public interface PeopleDaoMapper {
    
    public int insert(People people);
    
    public int update(People people);
    
    public List<People> listPeoples();
    
    public People getPeopleById(Long id);
}
