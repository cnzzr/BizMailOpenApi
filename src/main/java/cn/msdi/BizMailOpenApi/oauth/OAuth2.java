package cn.msdi.BizMailOpenApi.oauth;

import java.io.File;
import java.io.IOException;
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

import cn.msdi.BizMailOpenApi.BaseService;
import cn.msdi.BizMailOpenApi.OpenApiConst;

public final class OAuth2 extends BaseService {
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
					service.init();
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
			logger.error("企业邮接口Key配置文件 bizmail.properties 未找到", e);
		}
		String clientId = p.getValue("client_id");
		String clientSecret = p.getValue("client_secret");

		client = new OClient();
		client.setGrant_type("client_credentials");
		client.setClient_id(clientId); // AppID
		client.setClient_secret(clientSecret); // Secret

		return true;
	}

	public Token getToken() {
		if (token == null || !token.isValid()) {
			synchronized (lockObject) {
				token = requestToken();
				if (null != token)
					token.setCreateDate(new Date(System.currentTimeMillis()));
			}
		}

		return token;
	}

	private Token requestToken() {
		HttpRequest request = HttpRequest.post(OpenApiConst.TOKEN_URL);
		request.form(client.toPostForm());

		HttpResponse response = request.send();
		if (response.statusCode() == 200) {
			String body = response.bodyText();
			JsonParser jsonParser = new JsonParser();
			Token t = jsonParser.parse(body, Token.class);
			if (null != t && StringUtil.isNotBlank(t.getAccess_token())) {
				return t;
			}
		} else {
			logger.error("OAuth 验证授权失败 statusCode=" + response.statusCode());
		}

		// try {
		// String body = ApiPost(OpenApiConst.TOKEN_URL, client.toForm());
		// Token t = JSONPARSER.parse(body, Token.class);
		// return t;
		// } catch (BizMailException e) {
		// logger.error("OAuth 验证授权失败",e);
		// }

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
