package com.qq.exmail.openapi.model;

import jodd.json.meta.JSON;

import com.qq.exmail.openapi.BaseModel;
import com.qq.exmail.openapi.model.jodd.Result;

/**
 * 
 * Alias 帐号名
 * Name 姓名
 * Gender 性别：0=未设置，1=男，2=女
 * SlaveList 别名列表，用逗号分隔
 * Position 职位
 * Tel 联系电话
 * Mobile 手机
 * ExtId 编号
 * PartyList 部门列表，部门的根结点不包括在路径里面。比如部门所属： 腾
 * 讯/广州研发中心/企业邮箱，Value 为：广州研发中心/企业邮箱
 * OpenType 成员状态：1=启用，2=禁用
 * 
 * @author 张宗荣
 * 
 */
public final class BizUser extends BaseModel{
	@JSON(name = "Alias")
	private String alias;
	@JSON(name = "Name")
	private String name;
	@JSON(name = "Gender")
	private String gender;
	@JSON(name = "SlaveList")
	private String slaveList;
	@JSON(name = "Position")
	private String position;
	@JSON(name = "Tel")
	private String tel;
	@JSON(name = "Mobile")
	private String mobile;
	@JSON(name = "ExtId")
	private String extId;
	
	@JSON(name = "PartyPath")
	private String partyPath;
	
	@JSON(name = "OpenType")
	private String openType;
	
	//用于员工密码的属性
	@JSON(name = "Password")
	private String password;
	
	@JSON(name = "PartyList")
	private Result PartyList;
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getSlaveList() {
		return slaveList;
	}
	public void setSlaveList(String slaveList) {
		this.slaveList = slaveList;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getExtId() {
		return extId;
	}
	public void setExtId(String extId) {
		this.extId = extId;
	}
	public String getPartyPath() {
		return partyPath;
	}
	
	//TODO 替换为对象，所属部门
	/*
	 * 设置所属部门
	 * 1、传部门路径，用’/’分隔
	 * 2、根部门不需要传。如果空，则为根部门。部门要求是已存在的
	 * 3、如果是多个部门，传多个PartyPath
	 */
	public void setPartyPath(String partyPath) {
		this.partyPath = partyPath;
	}
	public String getOpenType() {
		return openType;
	}
	public void setOpenType(String openType) {
		this.openType = openType;
	}
	
	public Result getPartyList() {
		return PartyList;
	}
	public void setPartyList(Result partyList) {
		PartyList = partyList;
	}
	
	
//	@JSON(include=false)
//	Field[] _fields = ReflectUtil.getSupportedFields(getClass());
//	@Override
//	protected Field[] getField() {
//		return _fields;
//	}
}
