package com.qq.exmail.openapi.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jodd.json.JsonParser;
import jodd.util.StringUtil;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.qq.exmail.openapi.BaseService;
import com.qq.exmail.openapi.BizMailException;
import com.qq.exmail.openapi.OpenApiConst;
import com.qq.exmail.openapi.model.BizParty;
import com.qq.exmail.openapi.model.jodd.Item;
import com.qq.exmail.openapi.model.jodd.Result;


public class BizPartyService extends BaseService {
	/**
	 * 日志
	 */
	private static Logger logger = LogManager.getLogger(BizPartyService.class);
	
	/**
	 * JSON反序列化
	 */
	private JsonParser JSONPARSER = new JsonParser();
	
	/**
	 * 3.3.6 获取子部门列表
	 * 1、参数为空则返回根节点下部门清单
	 * @param dstPath
	 * @return 部门不存在返回null 否则 List
	 * @throws BizMailException
	 */
	public List<BizParty> query(String partyPath) throws BizMailException {
		String paramKey = "PartyPath";
		Map<String, String> queryParam = new LinkedHashMap<String, String>();
		queryParam.put(paramKey, StringUtil.isBlank(partyPath) ? "" : partyPath);
		try {
			String responseTxt = ApiGet(OpenApiConst.PARTY_LIST_URL, queryParam);
			logger.debug(responseTxt);
			// 返回示例： { "Count": 1, "List": [ { "Value": "测试部门" } ] }
			Result r = JSONPARSER.parse(responseTxt, Result.class);
			List<BizParty> partyList = new ArrayList<BizParty>(r.getCount());// 初始化大小
			if (null != r && null != r.getList()) {
				String dstPath = null;
				BizParty partyObj = null;
				String parentPath = StringUtil.isBlank(partyPath) ? "" : (partyPath + "/");
				for (Item item : r.getList()) {
					dstPath = parentPath + item.getValue();
					partyObj = new BizParty();
					partyObj.setDstPath(dstPath);
					partyList.add(partyObj);
				}
			}
			return partyList;
		} catch (BizMailException bme) {
			if ("1310".equals(bme.getErrcode())) {	// {   "errcode": "1310",   "error": "party_not_found" }
				return null;
			} else {
				throw bme;
			}
		}
	}
	
	/**
	 * 3.3.7 获取部门下成员列表
	 * @param partyPath 部门路径。如果查看根部门，置为空。
	 * @return 部门不存在返回null 否则 List
	 * @throws BizMailException
	 */
	public List<String> queyrUser(String partyPath) throws BizMailException{
		String paramKey = "PartyPath";
		
		Map<String, String> queryParam = new LinkedHashMap<String, String>();
		queryParam.put(paramKey, StringUtil.isBlank(partyPath) ? "" : partyPath);
		try {
			String responseTxt = ApiGet(OpenApiConst.PARTY_USERLIST_URL, queryParam);
			logger.debug(responseTxt);
			// 返回示例： { "Count": 1, "List": [ { "Value": "zzr@vip2.msdi.cn" } ] }
			Result r = JSONPARSER.parse(responseTxt, Result.class);
			List<String> userList = new ArrayList<String>(r.getCount());// 初始化大小
			if (null != r && null != r.getList()) {
				String user = null;
				for (Item item : r.getList()) {
					user = item.getValue();
					userList.add(user);
				}
			}
			return userList;
		} catch (BizMailException bme) {
			if ("1310".equals(bme.getErrcode())) {	// {   "errcode": "1310",   "error": "party_not_found" }
				return null;
			} else {
				throw bme;
			}
		}
	}

	/**
	 * 增加部门，注意事项：
	 * 1、同一部门重复添加亦返回 true
	 * 2、部门dstpath属性的规则为 一级部门/二级部门/三级部门，接口自动创建父节点
	 * @see com.qq.exmail.openapi.model.BizParty
	 * @param party
	 * @return true成功
	 * @throws BizMailException
	 */
	public boolean add(BizParty party) throws BizMailException {
		party.setAction(OpenApiConst.OP_ADD);
		String dstpath = party.getDstPath();
		if (StringUtil.isBlank(dstpath)) {
			throw new BizMailException("部门dstpath属性不能为空");
		}
		logger.debug("新增部门：" + party.toJson());
		// 不用检查源部门是否存在
		String responseBody = ApiPost(OpenApiConst.PARTY_SYNC_URL, party.toPostForm());
		return StringUtil.isBlank(responseBody);
	}

	/**
	 * 修改部门，可实现签名、移动位置，注意事项：
	 * 1、部门父节点变更后，人员自动移动
	 * @see com.qq.exmail.openapi.model.BizParty
	 * @param party
	 * @return
	 * @throws BizMailException
	 */
	public boolean modify(BizParty party) throws BizMailException {
		party.setAction(OpenApiConst.OP_MOD);
		if (StringUtil.isBlank(party.getDstPath())) {
			throw new BizMailException("部门dstpath属性不能为空");
		} else if (StringUtil.isBlank(party.getSrcPath())) {
			throw new BizMailException("部门srcpath属性不能为空");
		}
		logger.debug("修改部门：" + party.toJson());
		String responseBody = ApiPost(OpenApiConst.PARTY_SYNC_URL, party.toPostForm());
		return StringUtil.isBlank(responseBody);
	}
	
	/**
	 * 删除部门，注意事项：
	 * 1、部门下有成员时无法删除；
	 * 2、仅删除最后一级部门，如 传递A/B/C时 删除 C
	 * @see com.qq.exmail.openapi.model.BizParty
	 * @param party
	 * @return
	 * @throws BizMailException
	 */
	public boolean delete(BizParty party) throws BizMailException {
		party.setAction(OpenApiConst.OP_DEL);
		if (StringUtil.isBlank(party.getDstPath())) {
			throw new BizMailException("部门dstpath属性不能为空");
		}
		logger.debug("删除部门：" + party.toJson());
		String responseBody = ApiPost(OpenApiConst.PARTY_SYNC_URL, party.toPostForm());
		return StringUtil.isBlank(responseBody);
	}
}
