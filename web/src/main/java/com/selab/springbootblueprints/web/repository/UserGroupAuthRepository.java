package com.selab.springbootblueprints.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.selab.springbootblueprints.web.model.entity.UserGroupAuth;

public interface UserGroupAuthRepository extends JpaRepository<UserGroupAuth, Long>{
	
}
