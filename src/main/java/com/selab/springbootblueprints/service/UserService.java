package com.selab.springbootblueprints.service;

import com.selab.springbootblueprints.exception.UserGroupNotFoundException;
import com.selab.springbootblueprints.exception.UserNameValidationException;
import com.selab.springbootblueprints.exception.UserPasswordValidationException;
import com.selab.springbootblueprints.model.entity.projection.UserGroupVO;
import com.selab.springbootblueprints.model.entity.projection.UserPageableInfoVO;
import com.selab.springbootblueprints.model.entity.projection.UserVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
	void addUser(String name, String password, String groupName) throws UserNameValidationException, UserPasswordValidationException, UserGroupNotFoundException;
	void addUser(String name, String password) throws UserNameValidationException, UserPasswordValidationException;
	Optional<UserVO> getUser(long id);
	boolean isExist(String username);
	Page<UserPageableInfoVO> getAllUserList(Pageable pageable);
	void update(long id, String groupName) throws UserPasswordValidationException;
	void changePassword(long id, String password) throws UserPasswordValidationException;
	void disableUser(long id);
	void enableUser(long id);
	void removeUser(long id);
	List<UserGroupVO> getUserGroupList();
}
