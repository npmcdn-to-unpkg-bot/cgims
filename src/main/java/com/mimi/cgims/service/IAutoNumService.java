package com.mimi.cgims.service;

import java.io.Serializable;

public interface IAutoNumService<M extends Serializable, PK extends Serializable> extends IBaseService<M,PK>{

    int getNewestCount(int year,int month,int day);

}
