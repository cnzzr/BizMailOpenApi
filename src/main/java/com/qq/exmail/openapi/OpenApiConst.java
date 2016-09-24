package com.qq.exmail.openapi;

/**
 * BizMail OpenApi 常量，包含 接口地址、动作类型、帐号状态
 * 
 * @author 张宗荣
 * 
 */
public class OpenApiConst {
	/**
	 * 接口参数配置文件
	 */
	protected static final String BIZMAILCONFIG_FILE = "bizmail.properties";

	/**
	 * 腾讯企业邮箱采用 OAuth2.0 协议对第三方进行授权
	 * 请求参数：client_id：swanzhong client_secret ：
	 * 接口返回结果：
	 * {
	 * "access_token":"GHUSH-4qIXPScxa_OY0CbPS31W1OM24L_Ys9FCc7LtJyxjHD5OZafLh3Y8gM7gzDtp-GdQEY4dwFXk2qgnkwJA ",
	 * "token_type":"Bearer",
	 * "expires_in":86400,
	 * "refresh_token":""
	 * }
	 */
	public static final String TOKEN_URL = "https://exmail.qq.com/cgi-bin/token";
	
	/**
	 * 单点登录获取 Authkey
	 * 请求参数：Alias string 帐号名
	 * 接口返回结果：
	 * {
	 * "AuthKey":"登陆/读信验证 Key"
	 * }
	 */
	public static final String AUTH_URL = "http://openapi.exmail.qq.com:12211/openapi/mail/authkey";
	
	/**
	 * 一键登录（免密码登录）
	 * Get请求 https://exmail.qq.com/cgi-bin/login?fun=bizopenssologin&method=bizauth&agent=<agent>&user=<email>&ticket=<ticket>
	 * ticket=Authkey
	 * agent=管理员帐号
	 * email=用户邮箱
	 */
	public static final String LOGIN_URL = "https://exmail.qq.com/cgi-bin/login";

	public static final String LISTEN_URL = "http://openapi.exmail.qq.com:12211/openapi/listen";
	public static final String MAIL_NEWCOUNT_URL = "http://openapi.exmail.qq.com:12211/openapi/mail/newcount";

	public static final String USER_GET_URL = "http://openapi.exmail.qq.com:12211/openapi/user/get";
	public static final String USER_SYNC_URL = "http://openapi.exmail.qq.com:12211/openapi/user/sync";
	public static final String USER_LIST_URL = "http://openapi.exmail.qq.com:12211/openapi/user/list";
	public static final String USER_CHECK_URL = "http://openapi.exmail.qq.com:12211/openapi/user/check";
	
	public static final String USER_WXTOKEN_OPEN_URL = "http://openapi.exmail.qq.com:12211/openapi/user/openwxtoken";
	public static final String USER_WXTOKEN_CLOSE_URL = "http://openapi.exmail.qq.com:12211/openapi/user/closewxtoken";

	public static final String PARTY_LIST_URL = "http://openapi.exmail.qq.com:12211/openapi/party/list";
	public static final String PARTY_USERLIST_URL = "http://openapi.exmail.qq.com:12211/openapi/partyuser/list";
	public static final String PARTY_SYNC_URL = "http://openapi.exmail.qq.com:12211/openapi/party/sync";

	public static final String GROUP_ADD_URL = "http://openapi.exmail.qq.com:12211/openapi/group/add";
	public static final String GROUP_DEL_URL = "http://openapi.exmail.qq.com:12211/openapi/group/delete";
	public static final String GROUP_ADDMEMBER_URL = "http://openapi.exmail.qq.com:12211/openapi/group/addmember";
	public static final String GROUP_DELMEMBER_URL = "http://openapi.exmail.qq.com:12211/openapi/group/deletemember";

//	public static final String CLIENT_SECRET = "";
//	public static final String CLIENT_ID = "";

	public static final String OP_ADD = "2";
	public static final String OP_DEL = "1";
	public static final String OP_MOD = "3";

	public static final String DISABLE_USER = "2";
	public static final String ENABLE_USER = "1";
}
