package com.qq.exmail.openapi.model.jodd;

import java.util.ArrayList;
import java.util.List;

import jodd.json.meta.JSON;

/**
 * 接口返回值实体
 * 
 * @author 张宗荣
 * 
 */
public class Result {
	@JSON(name = "Count")
	Integer count;
	@JSON(name = "List")
	List<Item> list;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public List<Item> getList() {
		return list;
	}

	public void setList(List<Item> list) {
		this.list = list;
	}
	
	/**
	 * 获取Item对象中的 Value属性
	 * @return List
	 */
	public List<String> getItemValues() {
		if (null == list)
			return null;
		List<String> valueList = new ArrayList<String>(this.count);// 初始化大小
		String value = null;
		for (Item item : this.list) {
			value = item.getValue();
			valueList.add(value);
		}

		return valueList;
	}

}
