package com.selab.springbootblueprints.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.selab.springbootblueprints.model.entity.UserGroupAuth;

public interface UserGroupAuthRepository extends JpaRepository<UserGroupAuth, Long>{
	
}
