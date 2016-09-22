package com.qq.exmail.openapi;

import java.io.PrintStream;
import java.io.PrintWriter;

import com.qq.exmail.openapi.model.BizError;


public class BizMailException extends Exception {
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 7288702813583687028L;

	BizError bizError;
	Throwable nested;

	public BizMailException() {
		super();
		nested = null;
		bizError = null;
	}

	public BizMailException(String message) {
		super(message);
		nested = null;
		bizError = null;
	}

	public BizMailException(String message, Throwable throwable) {
		super(message);
		this.nested = throwable;
		bizError = null;
	}

	public BizMailException(String message, BizError bizerror) {
		super(message);
		this.nested = null;
		bizError = bizerror;
	}
	
	

	public String getMessage() {
		String message = "";
		if (nested != null)
			message += super.getMessage() + " (" + nested.getMessage() + ")";
		else
			message += super.getMessage();
		
		if (bizError != null) {
			message += "[" + bizError.getErrcode() + "]" + bizError.getError();
		}

		return message;
	}
	
	/**
	 * 获取 接口错误码
	 * @return String
	 */
	public String getErrcode(){
		return bizError !=null ? bizError.getErrcode() : "";
	}

	public String getNonNestedMessage() {
		return super.getMessage();
	}

	public Throwable getNested() {
		if (nested == null)
			return this;
		else
			return nested;
	}

	public void printStackTrace() {
		super.printStackTrace();
		if (nested != null)
			nested.printStackTrace();
	}

	public void printStackTrace(PrintStream printstream) {
		super.printStackTrace(printstream);
		if (nested != null)
			nested.printStackTrace(printstream);
	}

	public void printStackTrace(PrintWriter printwriter) {
		super.printStackTrace(printwriter);
		if (nested != null)
			nested.printStackTrace(printwriter);
	}
}
