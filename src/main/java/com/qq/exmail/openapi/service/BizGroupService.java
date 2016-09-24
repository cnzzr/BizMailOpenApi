package com.qq.exmail.openapi.service;

import jodd.util.StringUtil;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.qq.exmail.openapi.BaseService;
import com.qq.exmail.openapi.BizMailException;
import com.qq.exmail.openapi.OpenApiConst;
import com.qq.exmail.openapi.model.BizGroup;


public class BizGroupService extends BaseService {
	/**
	 * 日志
	 */
	private static Logger logger = LogManager.getLogger(BizPartyService.class);

	/**
	 * 3.3.9 添加邮件群组
	 * @param bizGroup
	 * @return 成功true，失败抛出异常
	 * @throws BizMailException
	 */
	public boolean add(BizGroup bizGroup) throws BizMailException{
		if(StringUtil.isBlank(bizGroup.getGroupName())){
			throw new BizMailException("群组名称 不能为空");
		} else if (StringUtil.isBlank(bizGroup.getGroupAdmin())){
			throw new BizMailException("群组管理者 不能为空");
		}
		
		bizGroup.setAction(OpenApiConst.OP_ADD);
		logger.debug("新增邮件群组：" + bizGroup.toJson());
		String responseTxt = ApiGet(OpenApiConst.GROUP_ADD_URL, bizGroup.serialize());// 添加成功无返回
		return true;
	}
}
