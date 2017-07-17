package com.backend.dao;

import org.springframework.stereotype.Repository;

import com.backend.domain.Category;

@Repository("categoryDaoImpl")
public class CategoryDaoImpl extends DaoImpl<Category, CategoryDao> implements CategoryDao {

    public CategoryDaoImpl() {
        super(CategoryDao.class);
    }

}
