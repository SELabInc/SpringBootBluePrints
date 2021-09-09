package com.selab.springbootblueprints.model.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "UserGroup")
@Data
@ToString(exclude = {"userList"})
public class UserGroup {

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "Name", unique = true)
	private String name;

	@OneToMany(mappedBy = "userGroup")
	@JsonIgnore
	private List<User> userList;
	
	@OneToMany(mappedBy = "userGroup", fetch = FetchType.LAZY)
	private List<UserGroupAuth> userGroupAuthList;
}
