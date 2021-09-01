package com.selab.springbootblueprints.service;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class UserServiceTests {

    @Setter(onMethod_ = @Autowired)
    private UserService service;

    private static final String TEST_USER_NAME = "TESTUSER";
    private static final String TEST_USER_PASSWORD = "TESTPASSWORD";
    private static final String EXIST_GROUP_NAME_FOR_TEST = "Admin";
    private static final String NON_EXIST_GROUP_NAME_FOR_TEST = "POO";
    private static final String NON_VALID_PASSWORD_CASE_1 = "abcd";
    private static final String NON_VALID_PASSWORD_CASE_2 = "abcdefghijklmnopqrstuw";
    private static final String NON_VALID_USER_NAME_CASE_1 = "§정보현§";
    private static final String NON_VALID_USER_NAME_CASE_2 = "abcd";
    private static final String NON_VALID_USER_NAME_CASE_3 = "qwertyuiopasdfgh";
    private static final String NON_VALID_USER_NAME_CASE_4 = "selab dev";

    @BeforeEach
    public void beforeEach() {

    }

    @AfterEach
    public void afterEach() {

    }

    @Test
    public void addUser() {

    }

    @Test
    public void addUserValid() {

    }

    @Test
    public void getUser() {

    }

    @Test
    public void isExist() {

    }

    @Test
    public void getAllUserList() {

    }

    @Test
    public void update() {

    }

    @Test
    public void changePassword() {

    }

    @Test
    public void disableUser() {

    }

    @Test
    public void enableUser(long id) {

    }

    @Test
    public void removeUser(long id) {

    }

    @Test
    public void getUserGroupList() {

    }

    @Test
    public void loadUserByUsername() {

    }
}
