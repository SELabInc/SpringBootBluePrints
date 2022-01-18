package com.selab.webexample.model.bean;

import org.springframework.security.core.GrantedAuthority;

public enum Auth implements GrantedAuthority {

	MEMBER,
	ADMIN;

	@Override
	public String getAuthority() {
		
		return "ROLE_"+this.name();
	}
}
