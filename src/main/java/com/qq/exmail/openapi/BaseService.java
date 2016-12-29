package com.qq.exmail.openapi;

import java.net.HttpURLConnection;
import java.util.LinkedHashMap;
import java.util.Map;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.json.JsonParser;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.qq.exmail.openapi.model.BizError;
import com.qq.exmail.openapi.oauth.OAuth2;

public class BaseService {
	/*
	 * 调用的方式有两种方式：
	 * POST 方式：在POST 请求加上access_token；
	 * GET 或者其他方式： 在HTTP HEAD 加上Authorization，将client_id 和client_secret 以
	 * BASE64 加密方式加密，即base64(client_id: client_secret)，将密文发送到请求信息中。
	 */
	
	/**
	 * 日志
	 */
	private static Logger logger = LogManager.getLogger(BaseService.class);

	public final String ENCODING = "UTF-8";
	
	/**
	 * Api接口 Post请求
	 * @param end 请求接口地址
	 * @param formData Map类型请求参数
	 * @return
	 * @throws BizMailException
	 */
	protected String ApiPost(String end, Map<String, Object> formData) throws BizMailException {
		String body = null;
		HttpRequest request = HttpRequest.post(end).formEncoding(ENCODING);
		request.form(formData);

//		String token = "Bearer" + " " + OAuth2.getInstance().getToken().getAccess_token();
//		request.header("Authorization", token, true);
		
		body = httpRequest(request);
		return body;
	}
	
	/**
	 * Api接口 Get请求
	 * @param end 请求接口地址
	 * @param queryMap Map类型请求参数
	 * @return
	 * @throws BizMailException
	 */
	protected String ApiGet(String end, Map<String, String> queryMap) throws BizMailException {
		String body = null;
		if (null == queryMap) {
			queryMap = new LinkedHashMap<String, String>(1);
		}
		//queryMap.put("access_token", OAuth2.getInstance().getToken().getAccess_token());
		
		HttpRequest request = HttpRequest.get(end);
		request.queryEncoding(ENCODING).query(queryMap);
		
		body = httpRequest(request);
		return body;
	}
	
	/**
	 * Api接口 Get请求
	 * @param end 请求接口地址
	 * @param queryString Get请求参数
	 * @return
	 * @throws BizMailException
	 */
	protected String ApiGet(String end, String queryString) throws BizMailException {
		HttpRequest request = HttpRequest.get(end);
		request.queryEncoding(ENCODING).queryString(queryString);
		
		String body = httpRequest(request);
		return body;
	}
	
	// 请求接口过程中由于网络或者超时原因，token票据失效或请求失败导致
	//   建议同一数据请求失败一次后再次请求，降低错误率
	// 1002 temporarily_unavailable 暂时不可用
	// 1200	invalid_token token值无效
	private String httpRequest(HttpRequest request) throws BizMailException {
		String body = null;
		
		//OAuth 验证授权
		String token = "Bearer" + " " + OAuth2.getInstance().getToken().getAccess_token();
		request.header("Authorization", token, true);

		int retry = 0;
		while (retry < 4) {//1,2,3
			retry++;
			HttpResponse response = request.send();
			if (response.statusCode() != HttpURLConnection.HTTP_OK) {
				throw new BizMailException("BizMail接口Api请求失败 statusCode=" + response.statusCode());
			}
			body = response.charset(ENCODING).bodyText();//fix 中文乱码
			if (body.indexOf("errcode") > 0) {
				JsonParser jsonParser = new JsonParser();
				BizError bizError = jsonParser.parse(body, BizError.class);
				if ("1200".equals(bizError.getErrcode())) {//重新获取 OAuth 验证授权 access_token
					//token过期 重试
					String ntoken = "Bearer " + OAuth2.getInstance().refresh().getAccess_token();
					request.header("Authorization", ntoken, true);
					request.timeout(8000);
				} else if ("1002".equals(bizError.getErrcode())) {
					request.timeout(16000);
					logger.error("企业邮接口出错 [1002] temporarily_unavailable");
				} else {
					throw new BizMailException("BizMail接口出错", bizError);
				}
				continue;
			}
			break;//正常情况仅需尝试一次
		}
		return body;
	}
}
