package com.selab.springbootblueprints.repository;

import com.selab.springbootblueprints.model.entity.User;
import com.selab.springbootblueprints.model.entity.projection.UserPageableInfoVO;
import com.selab.springbootblueprints.model.entity.projection.UserVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long>, QuerydslPredicateExecutor<User> {

	@EntityGraph("joinUserAuth")
	Optional<User> findByUsername(String name);

	@Query("SELECT u FROM User u LEFT JOIN FETCH u.userGroup WHERE u.id = :id")
	Optional<UserVO> findUserVoById(long id);

	@Modifying
	@Query("UPDATE User u SET u.enabled = :enabled WHERE u.id = :id")
	void updateEnabled(long id, boolean enabled);

	@Modifying
	@Query("UPDATE User u SET u.password = :password WHERE u.id = :id")
	void updatePassword(long id, String password);

	@EntityGraph("joinUserGroup")
	Page<UserPageableInfoVO> findAllBy(Pageable pageable);
}
