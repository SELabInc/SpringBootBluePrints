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
        // TODO bhjung test code
    }

    @AfterEach
    public void afterEach() {
        // TODO bhjung test code
    }

    @Test
    public void addUser() {
        // TODO bhjung test code
    }

    @Test
    public void addUserValid() {
        // TODO bhjung test code
    }

    @Test
    public void getUser() {
        // TODO bhjung test code
    }

    @Test
    public void isExist() {
        // TODO bhjung test code
    }

    @Test
    public void getAllUserList() {
        // TODO bhjung test code
    }

    @Test
    public void update() {
        // TODO bhjung test code
    }

    @Test
    public void changePassword() {
        // TODO bhjung test code
    }

    @Test
    public void disableUser() {
        // TODO bhjung test code
    }

    @Test
    public void enableUser(long id) {
        // TODO bhjung test code
    }

    @Test
    public void removeUser(long id) {
        // TODO bhjung test code
    }

    @Test
    public void getUserGroupList() {
        // TODO bhjung test code
    }

    @Test
    public void loadUserByUsername() {
        // TODO bhjung test code
    }
}
