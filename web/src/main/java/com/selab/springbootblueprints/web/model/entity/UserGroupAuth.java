package com.selab.springbootblueprints.web.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.selab.springbootblueprints.web.model.bean.Auth;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "UserGroupAuth")
@Getter
@Setter
@ToString
public class UserGroupAuth {
	
	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "UserGroupId")
	@ToString.Exclude
	@JsonIgnore
	private UserGroup userGroup;
	
	@Column(name = "Auth")
	@Enumerated(EnumType.STRING)
	private Auth auth;
}
