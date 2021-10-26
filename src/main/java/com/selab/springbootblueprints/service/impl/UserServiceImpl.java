package com.selab.springbootblueprints.service.impl;

import com.selab.springbootblueprints.exception.UserGroupNotFoundException;
import com.selab.springbootblueprints.exception.UserNameValidationException;
import com.selab.springbootblueprints.exception.UserPasswordValidationException;
import com.selab.springbootblueprints.model.bean.Auth;
import com.selab.springbootblueprints.model.bean.UserDetailsImpl;
import com.selab.springbootblueprints.model.bean.UserGroupVO;
import com.selab.springbootblueprints.model.bean.UserVO;
import com.selab.springbootblueprints.model.entity.User;
import com.selab.springbootblueprints.model.entity.UserGroup;
import com.selab.springbootblueprints.model.entity.UserGroupAuth;
import com.selab.springbootblueprints.repository.UserGroupRepository;
import com.selab.springbootblueprints.repository.UserRepository;
import com.selab.springbootblueprints.service.UserService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    @Setter(onMethod_ = @Autowired)
    private UserRepository userRepository;

    @Setter(onMethod_ = @Autowired)
    private UserGroupRepository userGroupRepository;

    @Setter(onMethod_ = @Autowired)
    private PasswordEncoder passwordEncoder;

    private static final String DEFAULT_USER_GROUP_NAME = "NotApproval";

    @Override
    public void addUser(String name, String password, String groupName)
            throws UserNameValidationException, UserPasswordValidationException, UserGroupNotFoundException {
        UserGroup group = userGroupRepository.findByName(groupName)
                .orElseThrow(NoResultException::new);
        if (group == null) {
            throw new UserGroupNotFoundException(String.format("Not found user group(name:%s)", groupName));
        } else if (!User.isValidName(name)) {
            throw new UserNameValidationException(String.format("User name is not valid {name:%s}", name));
        } else if (!User.isValidPassword(password)) {
            throw new UserPasswordValidationException();
        }

        String encryptionPassword = passwordEncoder.encode(password);
        userRepository.save(new User(name, encryptionPassword, group));
    }

    @Override
    public void addUser(String name, String password) throws UserNameValidationException, UserPasswordValidationException {
        addUser(name, password, DEFAULT_USER_GROUP_NAME);
    }

    @Override
    public UserVO getUser(long id) {
        User user = userRepository.findByIdWithUserGroup(id)
                .orElseThrow(NoResultException::new);

        return new UserVO(user);
    }

    @Override
    public boolean isExist(String username) {
        return userRepository.existByName(username);
    }

    @Override
    public Page<UserVO> getAllUserList(Pageable pageable) {
        Page<User> userPage = userRepository.findAllWithGroup(pageable);

        return userPage.map(UserVO::new);
    }

    @Override
    public void update(long id, String groupName)
            throws UserPasswordValidationException, UserGroupNotFoundException {

        userRepository.update(id, groupName); // TODO bhjung groupNotFound & userNotFound exception throw
    }

    @Override
    public void changePassword(long id, String password) throws UserPasswordValidationException {
        if (!User.isValidPassword(password)) {
            throw new UserPasswordValidationException("password is not valid");
        }

        String encryptedPassword = passwordEncoder.encode(password);
        userRepository.updatePassword(id, encryptedPassword);
    }

    @Override
    public void removeUser(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = userRepository.findByNameWithUserGroup(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User name not found (name: %s)", username)));

        Auth[] auths = userEntity.getUserGroup()
                .getUserGroupAuthList()
                .stream()
                .map(UserGroupAuth::getAuth)
                .toArray(Auth[]::new);

        return new UserDetailsImpl(userEntity, auths);
    }

    @Override
    public void disableUser(long id) {
        userRepository.updateEnabled(id, false);
    }

    @Override
    public void enableUser(long id) {
        userRepository.updateEnabled(id, true);
    }

    @Override
    public List<UserGroupVO> getUserGroupList() {
        return userGroupRepository.findAll().stream()
                .map(UserGroupVO::new)
                .collect(Collectors.toList());
    }
}
