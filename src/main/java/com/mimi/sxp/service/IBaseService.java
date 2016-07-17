package com.mimi.sxp.service;

import java.util.List;

import com.mimi.sxp.model.query.BaseQueryModel;
import com.mimi.sxp.util.pageUtil.CommonPageObject;

public interface IBaseService<M extends java.io.Serializable, PK extends java.io.Serializable, Q extends BaseQueryModel> {

	public void initData();

	public M save(M model);

	public void saveOrUpdate(M model);

	public void update(M model);

	public void merge(M model);

	public void delete(PK id);

	public void deleteObject(M model);

	public M get(PK id);

	public int countAll();

	public List<M> listAll();

	public CommonPageObject<M> listAll(int pn);

	public CommonPageObject<M> listAll(int pn, int pageSize);

	public CommonPageObject<M> pre(PK pk, int pn, int pageSize);

	public CommonPageObject<M> next(PK pk, int pn, int pageSize);

	public CommonPageObject<M> pre(PK pk, int pn);

	public CommonPageObject<M> next(PK pk, int pn);

	public List<M> saveBatch(List<M> modelList);

	public void saveOrUpdateBatch(List<M> modelList);

	public void updateBatch(List<M> modelList);

	public void mergeBatch(List<M> modelList);

	public void deleteBatch(List<PK> ids);

	public void deleteObjectBatch(List<M> modelList);
	
	public List<M> list(Q command);
	
	public List<M> strictList(Q command);

	public CommonPageObject<M> query(Q command);

	public CommonPageObject<M> strictQuery(Q command);

	public int count(Q command);

	public int strictCount(Q command);
	
	public void initIndex();
}
