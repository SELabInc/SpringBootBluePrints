package com.selab.springbootblueprints.service;

import com.selab.springbootblueprints.model.entity.projection.UserGroupVO;
import com.selab.springbootblueprints.model.entity.projection.UserPageableInfoVO;
import com.selab.springbootblueprints.model.entity.projection.UserVO;
import com.selab.springbootblueprints.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@SpringBootTest
@Transactional
public class UserServiceTests {

    private UserService service;
    private UserRepository repository;

    private final String testUserName = "TESTUSER";
    private final String testUserPassword = "TESTPASSWORD";
    private final String existGroupNameForTest = "Admin";
    private final String nonExistGroupName = "POO";
    private final long nonExistUserId = -1;
    private final long existUserId;
    private final String existUsername;

    private final String[] nonValidPasswordCases = new String[] {
            "abcd", "abcdefghijklmnopqrstuw", ""
    };
    private final String[] nonValidUsernameCases = new String[] {
            "abcd", "§정보현§", "qwertyuiopasdfgh", "selab dev", "; DROP TABLE USER;", "<html>HELLO!<html>", ""
    };


    @Autowired
    public UserServiceTests(UserService service, UserRepository repository) {
        this.service = service;
        this.repository = repository;

        UserPageableInfoVO existUserInfo = repository.findAllBy(Pageable.ofSize(1)).getContent().get(0);
        this.existUserId = existUserInfo.getId();
        this.existUsername = existUserInfo.getUsername();
    }

    @BeforeAll  // run before test start
    public static void beforeAll() {

    }

    @BeforeEach // run before each test
    public void beforeEach() {

    }

    @AfterEach
    public void afterEach() {

    }

    @AfterAll
    public static void afterAll() {

    }

    @Test
    public void addUserWithGroupTest() {
        service.addUser(testUserName, testUserPassword, existGroupNameForTest);

    }

    @Test
    public void addUserWithGroupValidTest() {
        Assertions.assertThrows(Exception.class, () -> service.addUser(testUserName, testUserPassword, nonExistGroupName));

        for (String nonValidUsername : nonValidUsernameCases) {
            Assertions.assertThrows(Exception.class, () ->
                    service.addUser(nonValidUsername, testUserPassword, existGroupNameForTest)
            );
        }

        for (String nonValidPassword : nonValidPasswordCases) {
            Assertions.assertThrows(Exception.class, () ->
                service.addUser(testUserName, nonValidPassword, existGroupNameForTest)
            );
        }
    }

    @Test
    public void addUserTest() {
        service.addUser(testUserName, testUserPassword);
    }

    @Test
    public void addUserValidTest() {
        for (String nonValidUsername : nonValidUsernameCases) {
            Assertions.assertThrows(Exception.class, () ->
                service.addUser(nonValidUsername, testUserPassword)
            );
        }

        for (String nonValidPassword : nonValidPasswordCases) {
            Assertions.assertThrows(Exception.class, () ->
                service.addUser(testUserName, nonValidPassword)
            );
        }
    }

    @Test
    public void getUserTest() {
        UserVO userVO = service.getUser(existUserId).get();
    }

    @Test
    public void isExistTest() {
        Assertions.assertTrue(service.isExist(existUsername));
        Assertions.assertFalse(service.isExist(""));
    }

    @Test
    public void getAllUserListTest() {
        service.getAllUserList(PageRequest.ofSize(10));
    }

    @Test
    public void updateTest() {
        service.update(existUserId, existGroupNameForTest);
    }

    @Test
    public void changePasswordTest() {
        service.changePassword(existUserId, testUserPassword);
    }

    @Test
    public void changePasswordValidTest() {
        for (String nonValidPassword : nonValidPasswordCases) {
            Assertions.assertThrows(Exception.class, () ->
                service.changePassword(existUserId, nonValidPassword)
            );
        }
    }

    @Test
    public void disableUserTest() {
        service.disableUser(existUserId);
    }

    @Test
    public void enableUserTest() {
        service.enableUser(existUserId);
    }

    @Test
    public void removeUserTest() {
        service.removeUser(existUserId);
    }

    @Test
    public void removeUserValidTest() {
        Assertions.assertThrows(Exception.class, () ->
            service.removeUser(nonExistUserId)
        );
    }

    @Test
    public void getUserGroupListTest() {
        List<UserGroupVO> groupVOS = service.getUserGroupList();
    }

    @Test
    public void loadUserByUsernameTest() {
        service.loadUserByUsername(existUsername);
    }

    @Test
    public void loadUserByUsernameValidTest() {
        Assertions.assertThrows(Exception.class, () ->
            service.loadUserByUsername("")
        );
    }
}
