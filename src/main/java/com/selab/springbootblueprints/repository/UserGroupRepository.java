package com.selab.springbootblueprints.repository;

import com.selab.springbootblueprints.model.entity.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {
	
	UserGroup findByName(String name);
}
