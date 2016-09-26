package cn.msdi.BizMailOpenApi.model;

import java.util.List;

import jodd.json.JsonParser;

import org.junit.Assert;
import org.junit.Test;

import cn.msdi.BizMailOpenApi.ConstString;

import com.qq.exmail.openapi.model.BizParty;
import com.qq.exmail.openapi.model.BizUser;
import com.qq.exmail.openapi.model.jodd.Item;
import com.qq.exmail.openapi.model.jodd.Result;
import com.qq.exmail.openapi.utils.ModelConvert;


public class JsonToModelTest {
	@Test
	public void testJsonParserUser() {
		JsonParser jsonParser = new JsonParser();
		BizUser bizUser = jsonParser.parse(ConstString.UserJson, BizUser.class);

		System.out.println(bizUser.serialize());

		Assert.assertEquals("02166@vip2.msdi.cn", bizUser.getAlias());
	}
	
	@Test
	public void testModelConvert(){
		JsonParser jsonParser = new JsonParser();
		BizUser bizUser = jsonParser.parse(ConstString.UserJson, BizUser.class);
		BizUser modBizuser = ModelConvert.toModUser(bizUser);
		Assert.assertTrue(modBizuser.getSlave().size() == 2);
		Assert.assertTrue(modBizuser.getPartyPath().size() == 3);
		
		Assert.assertNull(modBizuser.getPartyList());
		Assert.assertNull(modBizuser.getSlaveList());
		
		System.out.println(modBizuser.toJson());
		System.out.println(modBizuser.serialize());
	}
	

	@Test
	public void testJsonParserParty() {
		JsonParser jsonParser = new JsonParser();
		BizParty bizParty = jsonParser.parse(ConstString.PartyJson, BizParty.class);

		System.out.println(bizParty.serialize());

		Assert.assertEquals("数字工程中心/信息一室", bizParty.getDstPath());
		Assert.assertEquals("数字工程中心/信息室", bizParty.getSrcPath());
	}
	
	String jsonPartyItem = "{\"Count\":4,\"List\":[{\"Value\":\"安全部\"},{\"Value\":\"合同部\"},{\"Value\":\"综合管理部\"},{\"Value\":\"特殊/部门\"}]}";

	@Test
	public void testJsonToItem() {
		System.out.println("testJsonToItem");
		Result result = new JsonParser().map(Result.class).parse(jsonPartyItem);
		System.out.println(result);
		Assert.assertEquals(Integer.valueOf(4), result.getCount());

		List<Item> lm = result.getList();
		Assert.assertTrue(lm.get(0) instanceof Item);
		String value = ((Item) lm.get(0)).getValue();
		System.out.println(value);
		Assert.assertEquals("安全部", value);
	}
	
	String jsonAliasItem = "{\"Count\": 1,\"List\": [" + "{\"Email\": \"02166@vip2.msdi.cn\",\"Type\": 0}" + "," + "{\"Email\": \"dec@vip2.msdi.cn\",\"Type\": 2}" + "]}";

	@Test
	public void testJSONParserForItem() {
		System.out.println("testJSONParserForItem");
		Result result = new JsonParser().map(Result.class).parse(jsonAliasItem);
		System.out.println(result);

		List<Item> lm = result.getList();
		Assert.assertTrue(lm.get(0) instanceof Item);
		String email = ((Item) lm.get(0)).getEmail();
		System.out.println(email);
		Assert.assertEquals("02166@vip2.msdi.cn", email);
	}

}
