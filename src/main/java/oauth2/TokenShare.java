package oauth2;

/**
 * 处理 群集环境中多个应用共享问题
 * @author 02166
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
