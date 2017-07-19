package com.backend.dao;

import org.apache.ibatis.annotations.Mapper;

import com.backend.domain.Customer;

@Mapper
public interface CustomerDao extends Dao<Customer> {

}
