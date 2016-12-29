package oauth2;

/**
 * 用于解决应用部署于群集环境中多个应用服务器之间Token共享的问题
 *   因为BizMail仅能维护一个有效的 access_token
 *
 * @author 张宗荣
 *
 */
public interface TokenShare {
	/**
	 * 设置
	 * @param token
	 * @return true成功、false失败
	 * @see oauth2.Token
	 */
	public boolean put(Token token);
	
	/**
	 * 获取
	 * @return Token
	 * @see oauth2.Token
	 */
	public Token get();
	
	/**
	 * 移除
	 */
	public void remove();
}
