package com.backend.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.backend.domain.AbstractLongDomainEntity;
import com.backend.json.ResponseList;
import com.google.common.collect.FluentIterable;

/**
 * @param T
 *            The domain object
 * @param D
 *            The dao interface. It's used for the sqlSession OBJECT_MAPPER
 */

public abstract class DaoImpl<T extends AbstractLongDomainEntity, D extends Dao<T>> implements Dao<T> {

    protected static final Logger LOG = LoggerFactory.getLogger(DaoImpl.class);
    protected Class<D> daoImplClazz;

    @Autowired
    private SqlSession sqlSession;

    private D dao;

    public DaoImpl(final Class<D> clazzDao) {
        this.daoImplClazz = clazzDao;
    }

    @PostConstruct
    protected void setDao() {
        dao = sqlSession.getMapper(daoImplClazz);
    }

    public D getDao() {
        return dao;
    }

    @Override
    public void add(final T domain) {
        if (domain.getVersion() == null) {
            domain.setVersion(1L);
        }
        // createdDate
        domain.setCreatedDate(new Date());
        dao.add(domain);
    }

    @Override
    public void add(final Iterable<T> domains) {
        for(final T domain : domains) { // FIXME batch sql insert
            add(domain);
        }
    }

    @Override
    public void update(final Iterable<T> domains) {
        for(final T domain : domains) {// FIXME batch sql update
            update(domain);
        }
    }

    @Override
    public void remove(final Iterable<T> domains) {
        Collection<Long> ids = new ArrayList<>();
        for(T t : domains) {
            ids.add(t.getId());
        }
        dao.removes(ids);
    }

    @Override
    public ResponseList<T> getPage(final int pageSize, String cursorKey) {
        LOG.debug("getPage with limit {} , cursor {}", pageSize, cursorKey);
        /** update cursor */
        String cursor = cursorKey;
        if (StringUtils.isEmpty(cursor)) {
            cursor = "0";
        }

        final Collection<T> items = dao.getPageItems(pageSize, cursor);
        if (items == null || items.isEmpty()) {
            return new ResponseList<T>(Collections.<T> emptyList());
        }
        final ResponseList<T> responseList = new ResponseList<>(items);

        // final ResponseList<T> responseList = dao.getPage(pageSize, cursor);
        // if (responseList == null || responseList.getItems().isEmpty()) {
        // return new ResponseList<T>(Collections.<T> emptyList());
        // }
        final int total = count();

        String nextCursorKey;

        if (Integer.parseInt(cursor) + responseList.getItems().size() <= total - 1) {
            nextCursorKey = String.format("%s", Integer.parseInt(cursor) + responseList.getItems().size());
        }
        else {
            nextCursorKey = null;
        }

        final ResponseList<T> page = new ResponseList<T>(responseList.getItems(), nextCursorKey);
        page.withTotal(total).withLimit(responseList.getItems().size());

        // populate previous
        if (!"0".equals(cursor)) {
            final int previousCursor = Math.abs(Integer.parseInt(cursor) - pageSize);
            page.withReverseCursor(String.format("%s", previousCursor));
        }
        return page;
    }

    @Override
    public T findById(final Long id) {
        return dao.findById(id);
    }

    @Override
    public Collection<T> findByIds(final Collection<Long> ids) {
        return CollectionUtils.isEmpty(ids) ? Collections.<T> emptyList() : dao.findByIds(ids);
    }

    @Override
    public void update(final T domain) {
        if (domain.getVersion() == null) {
            domain.setVersion(1L);
        }
        domain.setUpdatedDate(new Date());
        dao.update(domain);
    }

    @Override
    public Collection<T> getAll() {
        return dao.getAll();
    }

    @Override
    public void remove(final Long id) {
        dao.remove(id);
    }

    @Override
    public void removes(Collection<Long> ids) {
        dao.removes(ids);
    }

    @Override
    public int count() {
        return dao.count();
    }

    @Override
    public Integer countWithFilters(final T domain) {
        return dao.countWithFilters(domain);
    }

    protected ResponseList<T> populatePages(final Collection<T> items, final int pageSize, final String cursorKey) {
        return populatePages(items, pageSize, cursorKey, null);
    }

    protected ResponseList<T> populatePages(final Collection<T> items, final int pageSize, final String cursorKey,
            final Integer totalCount) {

        if (CollectionUtils.isEmpty(items)) {
            return new ResponseList<T>(items);
        }

        int total;
        if (totalCount == null) {
            total = count();
        }
        else {
            total = totalCount;
        }

        if ("0".equals(cursorKey) && items.size() < pageSize) {
            total = items.size();
        }

        // limit = data.size();
        LOG.debug(" total records count : {} : Integer.parseInt(cursorKey) + items.size() : {} ", total,
                Integer.parseInt(cursorKey) + items.size());
        String nextCursorKey = null;

        if (items.size() == pageSize && Integer.parseInt(cursorKey) + items.size() < total) {
            nextCursorKey = String.format("%s", Integer.parseInt(cursorKey) + items.size());
        }

        LOG.debug("next cursorKey {}", nextCursorKey);

        final ResponseList<T> page = new ResponseList<T>(items, nextCursorKey);
        page.withTotal(total).withLimit(items.size());

        // populate previous
        if (!"0".equals(cursorKey)) {
            final int previousCursor = Math.abs(Integer.parseInt(cursorKey) - pageSize);
            page.withReverseCursor(String.format("%s", previousCursor));
        }

        return page;
    }

    @Override
    public Collection<T> findByFields(final T domain) {
        return dao.findByFields(domain);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ResponseList<T> getPageWithFields(final T domain, final int limit, String cursorKey, final String sortBy,
            final boolean isAscending) {
        LOG.debug(String.format("getPageWithFields with limit %s, cursor %s, sortBy %s, ascending %s", limit, cursorKey, sortBy,
                isAscending));
        String cursor = cursorKey;
        if (StringUtils.isEmpty(cursor)) {
            cursor = "0";
        }

        // final ResponseList<T> responseList = dao.getPageWithFields(domain, limit + 1, cursor, sortBy, isAscending);
        //
        // if (responseList == null || responseList.getItems().isEmpty()) {
        // return new ResponseList<T>(Collections.<T> emptyList());
        // }

        final Collection<T> items = dao.getPageItemsWithFields(domain, limit + 1, cursor, sortBy, isAscending);
        if (items == null || items.isEmpty()) {
            return new ResponseList<T>(Collections.<T> emptyList());
        }
        /**/
        final int totalCount = Integer.parseInt(cursor) + items.size();
        // countWithFilters(domain);
        return populatePages(getItemsWithLimit(items, limit), limit, cursor, totalCount);

    }

    @SuppressWarnings("rawtypes")
    public static Collection getItemsWithLimit(final Collection<?> items, final int limit) {
        return FluentIterable.from(items).limit(limit).toList();
    }

    @Override
    public Collection<T> getPageItems(int limit, String offset) {
        return getDao().getPageItems(limit, offset);
    }

    @Override
    public Collection<T> getPageItemsWithFields(T domain, int pageSize, String cursorKey, String sortBy, boolean isAscending) {
        return getDao().getPageItemsWithFields(domain, pageSize, cursorKey, sortBy, isAscending);
    }

}
