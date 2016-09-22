package com.qq.exmail.openapi.oauth;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.io.FileUtil;
import jodd.json.JsonParser;
import jodd.props.Props;
import jodd.util.ClassLoaderUtil;
import jodd.util.StringUtil;
import oauth2.Token;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.qq.exmail.openapi.OpenApiConst;


/**
 * OAuth 验证授权处理单例处理
 * 
 * @author 张宗荣
 *
 */
public final class OAuth2{
	private static Logger logger = LogManager.getLogger(OAuth2.class);
	
	// 使用单例模式
	private static OAuth2 service = null;
	private static Object lockObject = new Object();

	private OClient client = null;
	private Token token = null;

	/**
	 * 单例 得到类的实例
	 * 
	 * @return 返回当前类的实例
	 */
	public static OAuth2 getInstance() {
		if (service == null) {
			synchronized (lockObject) {
				if (service == null) {
					service = new OAuth2();
					boolean r = service.init();
					if (!r) {
						logger.error("企业邮接口配置文件 bizmail.properties 参数未配置");
					}
				}
			}
		}
		return service;
	}

	private OAuth2() {

	}

	private boolean init() {
		// 读取属性文件
		Props p = new Props();
		try {
			// 修改读取配置的路径的方法
			// File f = new File("bizmail.properties");// BizMailOpenApi\bizmail.properties
			URL url = ClassLoaderUtil.getResourceUrl("bizmail.properties");
			File f = FileUtil.toContainerFile(url);
			logger.debug(f.getAbsolutePath());
			p.load(f);
		} catch (IOException e) {
			logger.error("企业邮接口配置文件 bizmail.properties 未找到", e);
		}
		String clientId = p.getValue("client_id");
		String clientSecret = p.getValue("client_secret");

		client = new OClient();
		client.setGrant_type("client_credentials");
		client.setClient_id(clientId); // AppID
		client.setClient_secret(clientSecret); // Secret

		return StringUtil.isNotBlank(clientId) && StringUtil.isNotBlank(clientSecret);
	}
	
	public Token refresh(){
		if (token != null) {
			synchronized (lockObject) {
				token = null;
			}
			logger.info("企业邮接口 触发 [1200] invalid_token");
		}
		return getToken();
	}

	/**
	 * 获取OAuth 验证所需 access_token
	 * TODO Token有可能为null
	 * @return
	 */
	public Token getToken() {
		if (token == null || !token.isValid()) {
			synchronized (lockObject) {
				token = requestToken();
				if (null != token) {
					long now = System.currentTimeMillis() - 60000;//考虑Http请求的耗时预防 access_token 过期
					token.setCreateDate(new Date(now));
				}
			}
		}

		return token;
	}

	private Token requestToken() {
		HttpRequest request = HttpRequest.post(OpenApiConst.TOKEN_URL);
		request.form(client.toPostForm());

		HttpResponse response = request.send();
		if (response.statusCode() == HttpURLConnection.HTTP_OK) {
			String body = response.bodyText();
			JsonParser jsonParser = new JsonParser();
			Token t = jsonParser.parse(body, Token.class);
			if (null != t && StringUtil.isNotBlank(t.getAccess_token())) {
				logger.debug("access_token = " + t.getAccess_token());
				return t;
			}
		} else {
			logger.error("OAuth 验证授权失败 statusCode=" + response.statusCode());
		}

		return null;
	}

	/**
	 * 获取OAuth认证信息的 client_id
	 * @return
	 */
	public String getClientId() {
		return this.client == null ? null : this.client.getClient_id();
	}

}
