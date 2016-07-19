package com.mimi.cgims.service;

import java.io.Serializable;
import java.util.List;

public interface IBaseService<M extends Serializable, PK extends Serializable> {
     void initData();

    void initTestData();

    int count();

    List<M> list();

    M get(PK id);

    PK add(M model);

    void update(M model);

    void delete(PK id);
}
