package cn.msdi.BizMailOpenApi.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import jodd.util.StringUtil;

public class Md5Utils {
	/**
	 * Creates MD5 digest of a file.
	 */
	public static String md5(final String password) {
		MessageDigest md5Digest = null;
		try {
			md5Digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException ignore) {
		}

		byte[] digest = md5Digest.digest(password.getBytes());

		return StringUtil.toHexString(digest).toLowerCase();
	}
}
