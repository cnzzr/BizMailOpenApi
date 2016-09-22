package com.qq.exmail.openapi.model;

/**
 * OpenApi异常信息
 * 
 * @author 张宗荣
 *
 */
public class BizError {
	String errcode;
	String error;
	
	String errorMessage;
	
	public String getErrcode() {
		return errcode;
	}
	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
	public String getErrorMessage() {
		//TODO 转换为中文易理解的文字
		return errorMessage;
	}
}
