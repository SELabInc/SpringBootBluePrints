package com.selab.webexample.service.impl;

import com.selab.webexample.exception.UserGroupNotFoundException;
import com.selab.webexample.model.entity.projection.UserGroupVO;
import com.selab.webexample.model.entity.projection.UserPageableInfoVO;
import com.selab.webexample.model.entity.projection.UserVO;
import com.selab.webexample.service.UserService;
import com.selab.webexample.exception.UserNameValidationException;
import com.selab.webexample.exception.UserPasswordValidationException;
import com.selab.webexample.model.bean.Auth;
import com.selab.webexample.model.bean.UserDetailsImpl;
import com.selab.webexample.model.entity.User;
import com.selab.webexample.model.entity.UserGroup;
import com.selab.webexample.model.entity.UserGroupAuth;
import com.selab.webexample.repository.UserGroupRepository;
import com.selab.webexample.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserGroupRepository userGroupRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String DEFAULT_USER_GROUP_NAME = "None";

    @Override
    public void addUser(String name, String password, String groupName)
            throws UserNameValidationException, UserPasswordValidationException, UserGroupNotFoundException {

        if (!User.isValidName(name)) {
            throw new UserNameValidationException(String.format("User name is not valid {name:%s}", name));
        } else if (!User.isValidPassword(password)) {
            throw new UserPasswordValidationException();
        }

        Optional<UserGroup> groupOptional = userGroupRepository.findByName(groupName);
        if (groupOptional.isEmpty()) {
            throw new UserGroupNotFoundException(String.format("Not found user group(name:%s)", groupName));
        }

        String encryptionPassword = passwordEncoder.encode(password);
        userRepository.save(new User(name, encryptionPassword, groupOptional.get()));
    }

    @Override
    public void addUser(String name, String password) throws UserNameValidationException, UserPasswordValidationException {
        addUser(name, password, DEFAULT_USER_GROUP_NAME);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserVO> getUser(long id) {
        return userRepository.findUserVoById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExist(String username) {

        return userRepository.findByUsername(username)
                .isPresent();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserPageableInfoVO> getAllUserList(Pageable pageable) {
        return userRepository.findAllBy(pageable);
    }

    @Override
    public void update(long id, String groupName) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Optional<UserGroup> userGroupOptional = userGroupRepository.findByName(groupName);
            if (userGroupOptional.isPresent()) {
                user.setUserGroup(userGroupOptional.get());
            } else {
                log.error(String.format("user group field update fail: userGroup(name:%s) not found", groupName));
            }

            userRepository.save(user);
        } else {
            log.error(String.format("user update fail: user(id:%s) not found", id));
        }
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

    @Override   // for security.core.userdetails.UserDetailsService
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = userRepository.findByUsername(username)
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
    @Transactional(readOnly = true)
    public List<UserGroupVO> getUserGroupList() {
        return userGroupRepository.findAllBy();
    }
}
