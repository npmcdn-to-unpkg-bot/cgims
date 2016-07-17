package com.mimi.sxp.util.pageUtil;

import java.util.List;

import com.mimi.sxp.util.ResultMapUtil;

public class PageUtil {

	public static final int BEGIN_PAGE = 1;
	public static final int MIN_PAGE_SIZE = 1;
	public static final int MAX_PAGE_SIZE = Integer.MAX_VALUE;
	public static final int DEFAULT_PAGE_SIZE = 10;
	/**
	 * 不关心总记录数
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public static int getPageStart(int pageNumber, int pageSize) {
		return (pageNumber-PageUtil.BEGIN_PAGE) * pageSize;
	}

	/**
	 * 计算分页获取数据时游标的起始位置
	 * 
	 * @param totalCount
	 *            所有记录总和
	 * @param pageNumber
	 *            页码,从1开始
	 * @return
	 */
	public static int getPageStart(int totalCount, int pageNumber, int pageSize) {
		int start = getPageStart(pageNumber, pageSize);
		if (start >= totalCount) {
			start = 0;
		}
		return start;
	}

	public static <E> CommonPageObject<E> getPage(int totalCount,
			int pageNumber, List<E> items, int pageSize) {
		CommonPageObject<E> page = new CommonPageObject<E>();
		page.setItemTotalNum(totalCount);
		page.setCurPage(pageNumber);
		page.setPageSize(pageSize);
		page.setItems(items);
		return page;
	}

	public static <E> Object convert2ResultMap(CommonPageObject<E> page) {
		return ResultMapUtil.getResultMap(page.getItemTotalNum(),page.getPageTotal(), page.getCurPage(), page.getPageSize(),  page.getItems(), true, "");
	}

}
