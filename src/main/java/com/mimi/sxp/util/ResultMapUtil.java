package com.mimi.sxp.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultMapUtil {
	
	public static final String MAP_KEY_SUCCESS = "success";
	
	public static final String MAP_KEY_MSG = "msg";

	public static final String MAP_KEY_FIELD = "field";

	public static <E> Object getResultMap(int rows, int totalpage, int curPage,
			int pageSize, List<E> items, boolean rescode, String msg) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("totalrows", rows);
		map.put("curpage", curPage);
		map.put("totalpage", totalpage);
		map.put("pagesize", pageSize);

		resultMap.put("pageinfo", map);
		resultMap.put("items", items);

		resultMap.put(MAP_KEY_SUCCESS, rescode);
		resultMap.put(MAP_KEY_MSG, msg);

		return resultMap;
	}

	public static Map<String,Object> getResultMap(String field,String msg,boolean success){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put(MAP_KEY_FIELD, field);
		resultMap.put(MAP_KEY_SUCCESS, success);
		resultMap.put(MAP_KEY_MSG, msg);
		return resultMap;
	}

	public static Map<String,Object> getResultMap(String field,String msg){
		return getResultMap(field,msg,false);
	}
	
	public static Map<String,Object> getResultMap(String msg,boolean success){
		return getResultMap(null,msg,success);
	}
	
	/**
	 * 默认成功
	 * @param msg
	 * @return
	 */
	public static Map<String,Object> getResultMap(String msg){
		return getResultMap(null,msg,true);
	}

	/**
	 * 默认成功
	 * @param msg
	 * @return
	 */
	public static Map<String,Object> getResultMap(){
		return getResultMap(null,"成功",true);
	}
	
}
