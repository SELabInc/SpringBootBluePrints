package com.selab.springbootblueprints.repository;

import com.selab.springbootblueprints.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Long>{

	@Query("SELECT CASE WHEN COUNT(u)> 0 THEN true ELSE false END FROM User u WHERE u.username = ?1")
	boolean existByName(String name);
	
	@Query("SELECT u FROM User u LEFT JOIN FETCH u.userGroup WHERE u.id = :id")
	User findByIdWithUserGroup(Long id);
	
	@Query("SELECT u FROM User u LEFT JOIN FETCH u.userGroup g LEFT JOIN FETCH g.userGroupAuthList WHERE u.username = :name")
	User findByNameWithUserGroup(String name);
	
	@Query(value = "SELECT u FROM User u LEFT JOIN FETCH u.userGroup g",
			countQuery = "SELECT count(u) FROM User u")
	Page<User> findAllWithGroup(Pageable pageable);

	@Modifying
	@Query("UPDATE User u SET u.enabled = :enabled WHERE u.id = :id")
	void updateEnabled(long id, boolean enabled);

	@Modifying
	@Query("UPDATE User u SET u.userGroup = (SELECT g FROM UserGroup g WHERE g.name = :groupName) WHERE u.id = :id")
	void update(long id, String groupName);

	@Modifying
	@Query("UPDATE User u SET u.password = :password WHERE u.id = :id")
	void updatePassword(long id, String password);
}
