package com.qq.exmail.openapi.model.jodd;

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

}
