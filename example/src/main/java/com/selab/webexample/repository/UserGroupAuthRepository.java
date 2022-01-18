package com.selab.webexample.repository;

import com.selab.webexample.model.entity.UserGroupAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGroupAuthRepository extends JpaRepository<UserGroupAuth, Long>{
	
}
