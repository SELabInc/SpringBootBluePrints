package com.selab.springbootblueprints.model.entity;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "UserGroup")
@Getter
@Setter
public class UserGroup {

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "Name", unique = true)
	private String name;

	@OneToMany(mappedBy = "userGroup")
	@JsonIgnore
	@ToString.Exclude
	private List<User> userList;
	
	@OneToMany(mappedBy = "userGroup", fetch = FetchType.LAZY)
	@JsonIgnore
	@ToString.Exclude
	private List<UserGroupAuth> userGroupAuthList;
}
