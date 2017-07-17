package com.backend.dao;

import org.apache.ibatis.annotations.Mapper;

import com.backend.domain.Product;

@Mapper
public interface ProductDao extends Dao<Product> {

}
