package com.selab.springbootblueprints.model.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.selab.springbootblueprints.model.bean.Auth;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "UserGroupAuth")
@Data
@ToString(exclude = {"userGroup"})
public class UserGroupAuth implements GrantedAuthority {
	
	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "UserGroupId")
	@JsonIgnore
	private UserGroup userGroup;
	
	@Column(name = "Auth")
	private Auth auth;

	@Transient
	@Override
	public String getAuthority() {
		return auth.getAuthority();
	}
}
