package com.mimi.sxp.service.impl;

import java.util.List;

import com.mimi.sxp.dao.IBaseDao;
import com.mimi.sxp.model.query.BaseQueryModel;
import com.mimi.sxp.service.IBaseService;
import com.mimi.sxp.util.pageUtil.CommonPageObject;
import com.mimi.sxp.util.pageUtil.PageUtil;

public abstract class BaseService<M extends java.io.Serializable, PK extends java.io.Serializable,Q extends BaseQueryModel>
		implements IBaseService<M, PK,Q> {

	protected IBaseDao<M, PK,Q> baseDao;

	public abstract void setBaseDao(IBaseDao<M, PK,Q> baseDao);
	
	public void initData(){
		int count = baseDao.countAll();
		if (count < 1) {
			initAction();
		}
	}

	protected void initAction(){
		
	}
	
	public M save(M model) {
		baseDao.save(model);
		return model;
	}

	public void merge(M model) {
		baseDao.merge(model);
	}

	public void saveOrUpdate(M model) {
		baseDao.saveOrUpdate(model);
	}

	public void update(M model) {
		baseDao.update(model);
	}

	public void delete(PK id) {
		baseDao.delete(id);
	}

	public void deleteObject(M model) {
		baseDao.deleteObject(model);
	}

	public M get(PK id) {
		return baseDao.get(id);
	}

	public int countAll() {
		return baseDao.countAll();
	}

	public List<M> listAll() {
		return baseDao.listAll();
	}

	public CommonPageObject<M> listAll(int pn) {

		return this.listAll(pn, PageUtil.DEFAULT_PAGE_SIZE);
	}

	public CommonPageObject<M> listAllWithOptimize(int pn) {
		return this.listAllWithOptimize(pn, PageUtil.DEFAULT_PAGE_SIZE);
	}

	public CommonPageObject<M> listAll(int pn, int pageSize) {
		Integer count = countAll();
		List<M> items = baseDao.listAll(pn, pageSize);
		return PageUtil.getPage(count, pn, items, pageSize);
	}

	public CommonPageObject<M> listAllWithOptimize(int pn, int pageSize) {
		Integer count = countAll();
		List<M> items = baseDao.listAll(pn, pageSize);
		return PageUtil.getPage(count, pn, items, pageSize);
	}

	public CommonPageObject<M> pre(PK pk, int pn, int pageSize) {
		Integer count = countAll();
		List<M> items = baseDao.pre(pk, pn, pageSize);
		return PageUtil.getPage(count, pn, items, pageSize);
	}

	public CommonPageObject<M> next(PK pk, int pn, int pageSize) {
		Integer count = countAll();
		List<M> items = baseDao.next(pk, pn, pageSize);
		return PageUtil.getPage(count, pn, items, pageSize);
	}

	public CommonPageObject<M> pre(PK pk, int pn) {
		return pre(pk, pn, PageUtil.DEFAULT_PAGE_SIZE);
	}

	public CommonPageObject<M> next(PK pk, int pn) {
		return next(pk, pn, PageUtil.DEFAULT_PAGE_SIZE);
	}

	public List<M> saveBatch(List<M> modelList) {
		for (M model : modelList) {
			baseDao.save(model);
		}
		return modelList;
	}

	public void saveOrUpdateBatch(List<M> modelList) {
		for (M model : modelList) {
			baseDao.saveOrUpdate(model);
		}
	}

	public void updateBatch(List<M> modelList) {
		for (M model : modelList) {
			baseDao.update(model);
		}
	}

	public void mergeBatch(List<M> modelList) {
		for (M model : modelList) {
			baseDao.merge(model);
		}
	}

	public void deleteBatch(List<PK> ids) {
		for (PK id : ids) {
			baseDao.delete(id);
		}
	}

	public void deleteObjectBatch(List<M> modelList) {
		for (M model : modelList) {
			baseDao.deleteObject(model);
		}
	}

	public List<M> list(Q command){
		return baseDao.query(command);
	}

	public List<M> strictList(Q command){
		return baseDao.strictQuery(command);
	}

	public CommonPageObject<M> query(Q command){
		return PageUtil.getPage(baseDao.count(command), command.getTargetPage(), baseDao.query(command), command.getPageSize());
	}

	public CommonPageObject<M> strictQuery(Q command){
		return PageUtil.getPage(baseDao.strictCount(command), command.getTargetPage(), baseDao.strictQuery(command), command.getPageSize());
	}
	
	public int count(Q command){
		return baseDao.count(command);
	}

	public int strictCount(Q command){
		return baseDao.strictCount(command);
	}
	
	public void initIndex(){
		baseDao.initIndex();
	}
}
