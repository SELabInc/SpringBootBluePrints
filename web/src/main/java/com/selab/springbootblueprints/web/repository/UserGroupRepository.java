package com.selab.springbootblueprints.web.repository;

import com.selab.springbootblueprints.web.model.entity.UserGroup;
import com.selab.springbootblueprints.web.model.entity.projection.UserGroupVO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {
	Optional<UserGroup> findByName(String name);
	List<UserGroupVO> findAllBy();
	boolean existsByName(String name);
}
