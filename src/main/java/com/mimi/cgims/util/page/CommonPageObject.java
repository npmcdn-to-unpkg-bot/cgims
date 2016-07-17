package com.mimi.cgims.util.page;

import java.util.List;

public class CommonPageObject<E> {

	private List<E> items;// 当前页包含的记录列表
	private int curPage = 0;
	private int pageSize = 0;
	private int itemTotalNum = 0;
	private int pageTotal = 0;

	public List<E> getItems() {
		return items;
	}

	public void setItems(List<E> items) {
		this.items = items;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		refreshPageTotal();
	}

	public int getItemTotalNum() {
		return itemTotalNum;
	}

	public void setItemTotalNum(int itemTotalNum) {
		this.itemTotalNum = itemTotalNum;
		refreshPageTotal();
	}

	public int getPageTotal() {
		return pageTotal;
	}

	public void setPageTotal(int pageTotal) {
		this.pageTotal = pageTotal;
	}

	private void refreshPageTotal() {
		if (itemTotalNum > 0 && pageSize > 0) {
			int tempTotal = itemTotalNum / pageSize;
			if (itemTotalNum % pageSize > 0) {
				tempTotal++;
			}
			setPageTotal(tempTotal);
			if (curPage >= tempTotal) {
				curPage = tempTotal;
				if (curPage < 0) {
					curPage = 0;
				}
			}
		}
	}

}
