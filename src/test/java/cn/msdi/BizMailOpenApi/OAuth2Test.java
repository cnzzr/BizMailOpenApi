package cn.msdi.BizMailOpenApi;

import jodd.json.JsonParser;
import jodd.json.JsonSerializer;
import jodd.util.StringUtil;
import oauth2.Token;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.qq.exmail.openapi.oauth.OAuth2;


public class OAuth2Test {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testToken() {
		Token token = OAuth2.getInstance().getToken();
		Assert.assertTrue(token != null && StringUtil.isNotBlank(token.getAccess_token()));
		
		System.out.println("access_token = " + token.getAccess_token());

		//序列化测试
		String tokenJson = new JsonSerializer().serialize(token);
		System.out.println(tokenJson);
		System.out.println(tokenJson.length());

		//反序列化
		Token token2 = new JsonParser().parse(tokenJson,Token.class);
		if(token.getAccess_token().equals(token2.getAccess_token()) && token2.isValid()){
			System.out.println("Token 反序列化成功");
		}
	}

	@Test
	public void testTokenShare(){
		Token token = OAuth2.getInstance().getToken();
		Token tokenRefresh = OAuth2.getInstance().refresh();

		Assert.assertNotNull(token);
		Assert.assertNotNull(tokenRefresh);

		Assert.assertNotSame(token.getAccess_token(),tokenRefresh.getAccess_token());

	}

}
