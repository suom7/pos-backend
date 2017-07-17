package com.backend.dao;

import org.apache.ibatis.annotations.Mapper;

import com.backend.domain.Category;

@Mapper
public interface CategoryDao extends Dao<Category>{
    
}
