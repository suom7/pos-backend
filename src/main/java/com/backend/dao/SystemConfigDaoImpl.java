package com.backend.dao;

import org.springframework.stereotype.Repository;

import com.backend.domain.SystemConfig;

@Repository("systemConfigDaoImpl")
public class SystemConfigDaoImpl extends DaoImpl<SystemConfig, SystemConfigDao> implements SystemConfigDao {

    public SystemConfigDaoImpl() {
        super(SystemConfigDao.class);
    }
}
