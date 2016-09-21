package cn.msdi.BizMailOpenApi.oauth;

import java.util.LinkedHashMap;
import java.util.Map;

import jodd.util.StringUtil;

public class OClient {
	/**
	 * grant_type=client_credentials &client_id=E560D2CF-6B18-429E-9ED2-82D66703F757
	 * &client_secret=09835108-36CA-4AD2-B433-50039E5AA853
	 */
	private String grant_type;
	private String client_id;
	private String client_secret;

	public String getGrant_type() {
		return grant_type;
	}

	public void setGrant_type(String grant_type) {
		this.grant_type = grant_type;
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getClient_secret() {
		return client_secret;
	}

	public void setClient_secret(String client_secret) {
		this.client_secret = client_secret;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(128);
		sb.append("grant_type=").append(StringUtil.isNotEmpty(grant_type) ? grant_type : "");
		sb.append("&client_id=").append(StringUtil.isNotEmpty(client_id) ? client_id : "");
		sb.append("&client_secret=").append(StringUtil.isNotBlank(client_secret) ? client_secret : "");

		return sb.toString();
	}
	
	public Map<String, Object> toPostForm(){
		Map<String,Object> map = new LinkedHashMap<String, Object>();
		map.put("grant_type", this.grant_type);
		map.put("client_id", StringUtil.isNotEmpty(client_id) ? client_id : "");
		map.put("client_secret", StringUtil.isNotBlank(client_secret) ? client_secret : "");
		
		return map;
	}

}
