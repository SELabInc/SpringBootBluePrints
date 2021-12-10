package com.selab.springbootblueprints.model.bean;

import org.springframework.security.core.GrantedAuthority;

public enum Auth implements GrantedAuthority {

	COMMON,
	MEMBER,
	ADMIN;

	@Override
	public String getAuthority() {
		
		return "ROLE_"+this.name();
	}
}
