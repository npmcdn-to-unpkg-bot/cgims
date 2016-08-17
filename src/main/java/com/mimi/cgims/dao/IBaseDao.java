package com.mimi.cgims.dao;

import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;

public interface IBaseDao<M extends Serializable, PK extends Serializable> {
    Session getSession();

    int count();

    List<M> list();

    M get(PK id);

    PK add(M model);

    void update(M model);

    void delete(PK id);

    int batchDelete(PK... ids);

    int batchUpdate(String name, Object value, PK... ids);
}
