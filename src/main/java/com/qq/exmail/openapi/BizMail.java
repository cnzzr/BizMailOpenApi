package com.qq.exmail.openapi;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import jodd.io.FileUtil;
import jodd.props.Props;
import jodd.util.ClassLoaderUtil;
import jodd.util.StringUtil;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * 接口配置
 * @author 张宗荣
 *
 */
public class BizMail {
	private static Logger logger = LogManager.getLogger(BizMail.class);
	
	private static String domain;
	private static String clientId;
	private static String clientSecret;
	
	/**
	 * 获取邮箱域名
	 * @return String
	 */
	public static String getDomain() {
		return domain;
	}

	/**
	 * 获取OAuth认证信息的 client_id
	 * 
	 * @return String
	 */
	public static String getClientId() {
		return clientId != null ? clientId : null;
	}

	/**
	 * 获取接口Api Key
	 * 
	 * @return String
	 */
	protected static String getClientSecret() {
		return clientSecret != null ? clientSecret : null;
	}
	
	public static String getAlias(String account){
		return String.format("%1$s@%2$s", account, getDomain());
	}
	
	static {
		URL url = ClassLoaderUtil.getResourceUrl(OpenApiConst.BIZMAILCONFIG_FILE);// NULL
		if (null == url) {
			System.err.println("企业邮接口配置文件 bizmail.properties 未找到");
			logger.error("企业邮接口配置文件 bizmail.properties 未找到");
		}
		// 读取属性文件
		Props p = new Props();
		try {
			// 修改读取配置的路径的方法
			File f = FileUtil.toContainerFile(url);
			logger.debug(f.getAbsolutePath());
			p.load(f);
		} catch (IOException e) {
			logger.error("配置文件 bizmail.properties 读取出错", e);
		}

		domain = p.getValue("domain");
		clientId = p.getValue("client_id");
		clientSecret = p.getValue("client_secret");

		if (StringUtil.isBlank(clientId) || StringUtil.isBlank(clientSecret) || StringUtil.isBlank(domain)) {
			System.err.println("企业邮接口配置文件 bizmail.properties 参数[client_id,client_secret,domain]未配置");
		}
	}
}
