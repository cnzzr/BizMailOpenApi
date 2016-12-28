package oauth2.impl;

import oauth2.Token;
import oauth2.TokenShare;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.qq.exmail.openapi.BizMail;


public class TokenShareImpl implements TokenShare {
	private static Logger logger = LogManager.getLogger(BizMail.class);
	private static Token shareToken;

	@Override
	public boolean put(Token token) {
		if (token != null && token.isValid()) {
			logger.debug("TokenShareImpl.put:" + token.getAccess_token());
			TokenShareImpl.shareToken = token;
			return true;
		}
		return false;

	}

	@Override
	public Token get() {
		if (TokenShareImpl.shareToken != null && TokenShareImpl.shareToken.isValid()) {
			logger.debug("TokenShareImpl.get");
			return TokenShareImpl.shareToken;
		}
		return null;
	}

	@Override
	public void remove() {
		logger.debug("TokenShareImpl.remove");
		TokenShareImpl.shareToken = null;
	}

}
