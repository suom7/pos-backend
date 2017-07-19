package com.backend.dao;

import org.springframework.stereotype.Repository;

import com.backend.domain.Customer;

@Repository("customerDaoImpl")
public class CustomerDaoImpl extends DaoImpl<Customer, CustomerDao> implements CustomerDao {

    public CustomerDaoImpl() {
        super(CustomerDao.class);
    }

}
