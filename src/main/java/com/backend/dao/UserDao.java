package com.backend.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.backend.domain.User;

@Mapper
public interface UserDao {

    @Select("SELECT id, username, enabled FROM users WHERE username = #{username}")
    User findByUsername(@Param("username") String username);

    User findById(@Param("id") Long id);

    User create(User domain);
}
