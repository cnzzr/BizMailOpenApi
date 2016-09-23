package com.qq.exmail.openapi.model.jodd;

import jodd.json.meta.JSON;

public class Item {
	// ---------------------------------------------------------------- Object Alias
	@JSON(name = "Alias")
	String alias;
	@JSON(name = "Action")
	Integer action;

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Integer getAction() {
		return action;
	}

	public void setAction(Integer action) {
		this.action = action;
	}

	// ---------------------------------------------------------------- Object Email
	@JSON(name = "Email")
	String email;
	@JSON(name = "Type")
	Integer type;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	// ---------------------------------------------------------------- Object Value
	@JSON(name = "Value")
	String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
