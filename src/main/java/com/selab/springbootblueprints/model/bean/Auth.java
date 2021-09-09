package com.selab.springbootblueprints.model.bean;

public enum Auth {

	COMMON,
	MEMBER,
	ADMIN;

	public String getAuthority() {
		
		return "ROLE_"+this.name();
	}
}
