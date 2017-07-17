package com.backend.dao;

import java.util.Collection;
import org.apache.ibatis.annotations.Param;

import com.backend.json.ResponseList;

public interface Dao<T> {

    T findById(@Param("id") Long id);

    Collection<T> findByIds(@Param("list") Collection<Long> ids);

    void add(T domain);

    void add(Iterable<T> domains);

    void update(T domain);

    void update(Iterable<T> domains);

    Collection<T> getAll();

    void remove(@Param("id") Long id);

    void removes(@Param("list") Collection<Long> ids);

    void remove(Iterable<T> domains);

    int count();

    Integer countWithFilters(@Param("domain") T domain);

    Collection<T> findByFields(@Param("domain") T domain);

    ResponseList<T> getPage(int limit, String offset);

    Collection<T> getPageItems(@Param("limit") int limit, @Param("offset") String offset);

    ResponseList<T> getPageWithFields(T domain, int pageSize, String cursorKey, String sortBy, boolean isAscending);

    Collection<T> getPageItemsWithFields(@Param("domain") T domain, @Param("limit") int pageSize,
            @Param("offset") String cursorKey, @Param("sortBy") String sortBy, @Param("isAscending") boolean isAscending);
}
