package com.qq.exmail.openapi.model;

import java.util.List;

import jodd.json.meta.JSON;

import com.qq.exmail.openapi.BaseModel;

public final class BizGroup extends BaseModel{
	public static String STATUS_ALL = "all";
	public static String STATUS_INNER = "inner";
	public static String STATUS_GROUP = "group";
	public static String STATUS_LIST = "list";
	
	@JSON(name="group_name ")
	private String groupName;
	
	@JSON(name="group_admin")
	private String groupAdmin;
	
	private String status;
	
	@JSON(name="members",include=true)//Must
	private List<String> members;
	
	public String getGroupName() {
		return groupName;
	}
	/**
	 * 群组名称，注：不应当包含邮箱域名
	 * @param groupName
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupAdmin() {
		return groupAdmin;
	}
	/**
	 * 群组管理者（需要使用一个域中不存在的邮箱地址）
	 * @param groupAdmin
	 */
	public void setGroupAdmin(String groupAdmin) {
		this.groupAdmin = groupAdmin;
	}
	public String getStatus() {
		return status;
	}
	/**
	 * 群组状态（分为 4 类 all，inner，group，list）
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	public List<String> getMembers() {
		return members;
	}
	/**
	 * 成员帐号
	 * @param members
	 */
	public void setMembers(List<String> members) {
		this.members = members;
	}


}
