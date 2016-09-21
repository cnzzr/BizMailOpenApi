package cn.msdi.BizMailOpenApi;

import jodd.util.StringUtil;

import oauth2.Token;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cn.msdi.BizMailOpenApi.oauth.OAuth2;

public class OAuth2Test {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testToken() {
		Token token = OAuth2.getInstance().getToken();
		Assert.assertTrue(token != null && StringUtil.isNotBlank(token.getAccess_token()));
		System.out.println(token.getAccess_token());
	}

}
