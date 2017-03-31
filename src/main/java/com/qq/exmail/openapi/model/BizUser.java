package com.qq.exmail.openapi.model;

import java.util.ArrayList;
import java.util.List;

import com.qq.exmail.openapi.BizMail;
import com.qq.exmail.openapi.utils.Md5Utils;
import jodd.json.meta.JSON;

import com.qq.exmail.openapi.BaseModel;
import com.qq.exmail.openapi.model.jodd.Result;

/**
 * 成员资料对象
 * Alias 帐号名
 * Name 姓名
 * Gender 性别：0=未设置，1=男，2=女
 * Position 职位
 * Tel 联系电话
 * Mobile 手机
 * ExtId 编号
 * OpenType 成员状态：1=启用，2=禁用
 * 
 * <p>查询时</br>
 * PartyList 部门列表，部门的根结点不包括在路径里面。比如部门所属： 腾讯/广州研发中心/企业邮箱，Value 为：广州研发中心/企业邮箱</br>
 * SlaveList 别名列表，用逗号分隔
 * </p> 
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
	@JSON(name = "Position")
	private String position;
	@JSON(name = "Tel")
	private String tel;
	@JSON(name = "Mobile")
	private String mobile;
	@JSON(name = "ExtId")
	private String extId;
	@JSON(name = "OpenType")
	private String openType;
	
	@JSON(name = "PartyPath")
	private List<String> partyPath;	//替换为对象，所属部门
	@JSON(name="Slave")
	private List<String> slave;
	
	
	//用于员工密码的属性
	@JSON(name = "Password")
	private String password;

	@JSON(name = "Md5")
	private String md5;

	//---------------- 以下属于用于 获取成员资料后执行序列化
	@JSON(name = "PartyList")
	private Result PartyList;
	@JSON(name = "SlaveList")
	/**
	 * 如："SlaveList": "bb@gzdev.com,bo@gzdev.com"
	 */
	private String slaveList;
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
//		this.password = password;
		String md5Pwd = Md5Utils.md5(password);
		setMd5("1");
		this.password = md5Pwd;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
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

	/**
	 * 编号
	 * @return
     */
	public String getExtId() {
		return extId;
	}

	/**
	 * 设置编号
	 * @param extId
     */
	public void setExtId(String extId) {
		this.extId = extId;
	}
	
	public List<String> getPartyPath() {
		return partyPath;
	}
	/**
	 * 批量设置所属部门
	 * 1、传部门路径，用’/’分隔
	 * 2、根部门不需要传。如果空，则为根部门。部门要求是已存在的
	 * @param partyPath
	 */
	public void setPartyPath(List<String> partyPath) {
		this.partyPath = partyPath;
	}
	/**
	 * 设置所属部门
	 * 1、传部门路径，用’/’分隔
	 * 2、根部门不需要传。如果空，则为根部门。部门要求是已存在的
	 * 3、如果属于多个部门，则添加多个PartyPath
	 * @param partyPath
	 */
	public void addPartypath(String partyPath){
		if(null == this.partyPath){
			this.partyPath = new ArrayList<String>();
		}
		this.partyPath.add(partyPath);
	}
	
	public List<String> getSlave() {
		return slave;
	}
	/**
	 * 设置别名，注意系统限制五个
	 */
	@Deprecated
	public void setSlave(List<String> slave) {
		this.slave = slave;
	}

	/**
	 * 添加单个别名，系统上限为5个，别名如未包括 @域名 则自动增加
	 * 
	 * @param slave
	 * @return true成功、false失败
	 */
	public boolean addSlave(String slave) {
		if (null == this.slave) {
			this.slave = new ArrayList<String>();
		}
		int pos = slave.indexOf('@');
		if (pos < 0) {
			slave = BizMail.getAlias(slave);
		}
		if (this.slave.size() >= 5 && !this.slave.contains(slave)) {
			return false;
		}
		this.slave.add(slave);
		return true;
	}
	
	public String getOpenType() {
		return openType;
	}
	public void setOpenType(String openType) {
		this.openType = openType;
	}
	
	/**
	 * 别名列表，用逗号分隔
	 * @return
	 */
	public String getSlaveList() {
		return slaveList;
	}
	/**
	 * 仅供序列化调用
	 * @param slaveList
	 */
	public void setSlaveList(String slaveList) {
		this.slaveList = slaveList;
	}
	/**
	 * 复杂对象：部门列表，部门的根结点不包括在路径里面。
	 * @return
	 */
	public Result getPartyList() {
		return PartyList;
	}
	/**
	 * 仅供序列化调用
	 * @param partyList
	 */
	public void setPartyList(Result partyList) {
		PartyList = partyList;
	}
	
}
