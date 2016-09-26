package cn.msdi.BizMailOpenApi.service;

import java.util.List;

import oauth2.Token;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.qq.exmail.openapi.BizMailException;
import com.qq.exmail.openapi.model.BizParty;
import com.qq.exmail.openapi.oauth.OAuth2;
import com.qq.exmail.openapi.service.BizPartyService;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PartyServiceTest {
	BizPartyService partyService =null;
	@Before
	public void setUp() throws Exception {
		Token token = OAuth2.getInstance().getToken();
		partyService = new BizPartyService();
	}

	String party1 = "企业邮箱测试";
	String party2 = "测试二级部门";
	String dstPath = party1 + "/" + party2;
	
	@Test
	public void testAddAndQuery() throws BizMailException {
		testAddParty();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		testQuerySubParty();
	}
	
	public void testAddParty() throws BizMailException {
		System.out.println("测试添加部门");
		
		BizParty bizParty = new BizParty();
		bizParty.setDstPath(dstPath);		
		Boolean r = partyService.add(bizParty);
		
		Assert.assertTrue(r);
	}
	public void testQuerySubParty() throws BizMailException {
		System.out.println("测试查询部门");
		
		List<BizParty> listRoot = partyService.query("");
		System.out.println(listRoot.get(0).getDstPath());
		Assert.assertTrue(listRoot.size()>0);
		
		List<BizParty> listLevel1 = partyService.query(party1);
		System.out.println(listLevel1.get(0).getDstPath());
		Assert.assertTrue(listLevel1.size()>0);
	}
	
	@Test
	public void testDeleteParty() throws BizMailException {
		System.out.println("测试删除部门");
		
		//删除二级部门
		BizParty bizParty = new BizParty();
		bizParty.setDstPath(dstPath);
		Boolean r2 = partyService.delete(bizParty);
		Assert.assertTrue(r2);
		
		
		//删除一级部门
//		List<BizParty> listLevel1 = partyService.query(party1);
		while (partyService.query(party1).size()>0) {
			System.out.println("删除父节点前延迟处理……");
		}
		BizParty bizParty1 = new BizParty();
		bizParty1.setDstPath(party1);
		Boolean r1 = partyService.delete(bizParty1);
		Assert.assertTrue(r1);
	}
	
}
