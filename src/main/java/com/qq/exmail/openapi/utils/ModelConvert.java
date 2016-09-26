package com.qq.exmail.openapi.utils;

import java.util.List;

import jodd.bean.BeanCopy;

import org.jsoup.helper.StringUtil;

import com.qq.exmail.openapi.model.BizUser;

public class ModelConvert {

	/**
	 * 查询员工资料后的BizUser对象转换为 同步成员帐号资料所需对象，转换别名、部门列表属性
	 * 
	 * @see com.qq.exmail.openapi.model.BizUser
	 * @param bizUser
	 * @return
	 */
	public static BizUser toModUser(BizUser bizUser) {
		BizUser modBizUser = new BizUser();
		BeanCopy.fromBean(bizUser).toBean(modBizUser).copy();
		// 处理查询操作的 List属性
		if (null != modBizUser.getPartyList()) { // 所属部门
			List<String> partyList = modBizUser.getPartyList().getItemValues();
			modBizUser.setPartyPath(partyList);
			modBizUser.setPartyList(null);// 清除
		}

		if (!StringUtil.isBlank(modBizUser.getSlaveList())) {
			String slave = modBizUser.getSlaveList();
			String[] sla = slave.split(",");
			// 别名不会超过系统限制
			for (String sl : sla) {
				modBizUser.addSlave(sl);
			}
			modBizUser.setSlaveList(null);// 清除
		}

		return modBizUser;
	}
}
