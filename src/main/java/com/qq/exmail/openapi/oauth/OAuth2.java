package com.qq.exmail.openapi.oauth;

import java.net.HttpURLConnection;
import java.util.Date;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.json.JsonParser;
import jodd.util.StringUtil;
import oauth2.Token;
import oauth2.TokenShare;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.qq.exmail.openapi.BizMail;
import com.qq.exmail.openapi.OpenApiConst;


/**
 * OAuth 验证授权处理单例处理
 *
 * @author 张宗荣
 *
 */
public final class OAuth2 extends BizMail{
	private static Logger logger = LogManager.getLogger(OAuth2.class);

	// 使用单例模式
	private static OAuth2 service = null;
	private static Object lockObject = new Object();

	private OClient client = null;
	private Token token = null;
	private TokenShare tshare = null;

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
						logger.error("企业邮接口配置文件 bizmail.properties 参数[client_id,client_secret]未配置");
					}
				}
			}
		}
		return service;
	}

	private OAuth2() {

	}

	private boolean init() {
//		Props p = new Props();
//		try {
//			// 修改读取配置的路径的方法
//			// File f = new File("bizmail.properties");// BizMailOpenApi\bizmail.properties
//			URL url = ClassLoaderUtil.getResourceUrl("bizmail.properties");
//			File f = FileUtil.toContainerFile(url);
//			logger.debug(f.getAbsolutePath());
//			p.load(f);
//		} catch (IOException e) {
//			logger.error("企业邮接口配置文件 bizmail.properties 未找到", e);
//		}
//		String clientId = p.getValue("client_id");
//		String clientSecret = p.getValue("client_secret");

		// 通过配置文件获取属性
		String clientId = BizMail.getClientId();
		String clientSecret = BizMail.getClientSecret();

		client = new OClient();
		client.setGrant_type("client_credentials");
		client.setClient_id(clientId); // AppID
		client.setClient_secret(clientSecret); // Secret

		String tokenShare = BizMail.getTokenShareClass();
		if (StringUtil.isNotBlank(tokenShare)) {
			try {
				tshare = (TokenShare) Class.forName(tokenShare).newInstance();
			} catch (ClassNotFoundException cne) {
				//配置有误
			} catch(Exception exc){

			}
		}

		return StringUtil.isNotBlank(clientId) && StringUtil.isNotBlank(clientSecret);
	}

	/**
	 * 强制刷新token
	 * @return Token
	 */
	public Token refresh(){
//		if (token != null) {
//			synchronized (lockObject) {
//				token = null;
//			}
//			logger.info("企业邮接口 触发 [1200] invalid_token");
//
//			if(null!=tshare){
//				tshare.remove();
//			}
//		}
		logger.info("企业邮接口 触发 [1200] invalid_token");
		return getToken(true);
	}

	/**
	 * 获取OAuth 验证所需 access_token
	 * TODO Token有可能为null
	 * @return
	 */
	public Token getToken(){
		return getToken(false);
	}

	public Token getToken(boolean refresh) {
        // 1、应用首次启动从 TokenShare中加载，null或无效则重新请求；2、Refresh时直接请求Token
        if (null != tshare) {
            if (!refresh) {
                Token tokenFromShare = tshare.get();
                if (tokenFromShare != null && tokenFromShare.isValid()
                        && (token == null || !token.getAccess_token().equals(tokenFromShare.getAccess_token())) // 确保共享Token为新值
                        ) {
                    synchronized (lockObject) {
                        token = tokenFromShare;
                        return token;
                    }
                }
            }
        }

        if (refresh || token == null || !token.isValid()) {
            synchronized (lockObject) {
                Token tokenRefresh = requestToken();
                if (null != tokenRefresh) {
                    long now = System.currentTimeMillis() - 60000;// 考虑Http请求的耗时预防 access_token 过期
                    tokenRefresh.setCreateDate(new Date(now));
                    token = tokenRefresh;
                }
            }
            if (null != tshare && null != token) {
                tshare.put(token);
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
}
