package com.qq.exmail.openapi.model;

import jodd.json.meta.JSON;

import com.qq.exmail.openapi.BaseModel;

/**
 * 部门实体
 * 
 * @author 张宗荣
 *
 */
public final class BizParty extends BaseModel {
	@JSON(name="srcpath")
	String srcPath;
	@JSON(name="dstpath")
	String dstPath;



	public String getSrcPath() {
		return srcPath;
	}

	/**
	 * 源部门（注：部门用 '/' 分隔，根部门不用加部门最多 5 级，单个部门字符不超过 64 个字符）
	 * @param srcPath
	 */
	public void setSrcPath(String srcPath) {
		this.srcPath = srcPath;
	}

	public String getDstPath() {
		return dstPath;
	}

	/**
	 * 目标部门
	 * @param dstPath
	 */
	public void setDstPath(String dstPath) {
		this.dstPath = dstPath;
	}
	
//	Field[] fields = BizParty.class.getDeclaredFields();//无法获取到父类的属性
//	@JSON(name="field",include=false)
//	Field[] _fields = ReflectUtil.getSupportedFields(getClass());
//
//	@Override
//	protected Field[] getField() {
//		return _fields;
//	}

}
