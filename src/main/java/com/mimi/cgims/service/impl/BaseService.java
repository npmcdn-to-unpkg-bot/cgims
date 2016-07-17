package com.mimi.cgims.service.impl;

import com.mimi.cgims.dao.IBaseDao;
import com.mimi.cgims.service.IBaseService;

import java.io.Serializable;
import java.util.List;

public abstract class BaseService<M extends Serializable, PK extends Serializable>
        implements IBaseService<M, PK> {

    protected IBaseDao<M, PK> baseDao;

    public abstract void setBaseDao(IBaseDao<M, PK> baseDao);

    public void initData() {
        int count = baseDao.count();
        if (count < 1) {
            initAction();
        }
    }

    protected void initAction() {
    }

    public int count() {
        return baseDao.count();
    }

    public List<M> list() {
        return baseDao.list();
    }

    public M get(PK id) {
        return baseDao.get(id);
    }

    public PK add(M model) {
        return baseDao.add(model);
    }

    public void update(M model) {
        baseDao.update(model);
    }

    public void delete(PK id) {
        baseDao.delete(id);
    }

}
