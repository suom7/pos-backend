package com.backend.dao;

import org.springframework.stereotype.Repository;

import com.backend.domain.Product;

@Repository("productDaoImpl")
public class ProductDaoImpl extends DaoImpl<Product, ProductDao> implements ProductDao {

    public ProductDaoImpl() {
        super(ProductDao.class);
    }

}
