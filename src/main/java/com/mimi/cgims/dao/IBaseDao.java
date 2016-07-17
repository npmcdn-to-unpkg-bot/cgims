package com.mimi.cgims.dao;

import java.io.Serializable;
import java.util.List;

public interface IBaseDao<M extends Serializable, PK extends Serializable> {
    int count();

    List<M> list();

    M get(PK id);

    PK add(M model);

    void update(M model);

    void delete(PK id);
}
