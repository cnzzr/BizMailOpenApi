package cn.msdi.BizMailOpenApi.utils;


import org.junit.Assert;
import org.junit.Test;

import com.qq.exmail.openapi.utils.Md5Utils;

public class Md5Test {

	@Test
	public void testMd5() {
		String password = "1oNtiBLebaYlv";
		String md5String = Md5Utils.md5(password);
		System.out.println(md5String);
		
		Assert.assertEquals("2563be19fea55657e9d191f74b93a187", md5String);
	}
	
	@Test
	public void testMd5_abc123() {
		String password = "abc123";
		String md5String = Md5Utils.md5(password);
		System.out.println("MD5(abc123) = " + md5String);
		
		Assert.assertEquals("e99a18c428cb38d5f260853678922e03", md5String);
	}

}
