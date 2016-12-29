package oauth2;

import java.io.Serializable;
import java.util.Date;

/**
 * OAuth2 token类
 *
 * @author 张宗荣
 * @description OAuth2 Token
 */
public class Token implements Serializable{

	private String access_token;
	private String refresh_token;
	private Date createDate;
	private Long expires_in;//单位为 秒
	private String token_type;

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getRefresh_token() {
		return refresh_token;
	}

	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getToken_type() {
		return token_type;
	}

	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}

	public Long getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(Long expires_in) {
		this.expires_in = expires_in;
	}

	/**
	 * 判断Token是否过期
	 *
	 * @return true 有效，false 无效
     */
	public boolean isValid() {
		Date now = new Date();
		Long nowTimeStamp = now.getTime();
		Long createTimeStmap = createDate.getTime();
		return nowTimeStamp - createTimeStmap < (expires_in * 1000 );
	}

}
