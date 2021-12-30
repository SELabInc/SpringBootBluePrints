package com.selab.springbootblueprints.repository;

import com.selab.springbootblueprints.model.entity.UserGroupAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGroupAuthRepository extends JpaRepository<UserGroupAuth, Long>{
	
}
