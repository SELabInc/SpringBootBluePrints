package com.selab.springbootblueprints.service;

import com.selab.springbootblueprints.model.bean.Auth;
import com.selab.springbootblueprints.model.entity.User;
import com.selab.springbootblueprints.model.entity.UserGroup;
import com.selab.springbootblueprints.model.entity.UserGroupAuth;
import com.selab.springbootblueprints.model.entity.projection.UserGroupVO;
import com.selab.springbootblueprints.model.entity.projection.UserVO;
import com.selab.springbootblueprints.repository.UserGroupRepository;
import com.selab.springbootblueprints.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@Slf4j
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserServiceTests {

    private final UserService service;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserGroupRepository groupRepository;

    private final String TEST_USER_NAME = "TESTUSER";
    private final String TEST_USER_PASSWORD = "TESTPASSWORD";
    private final String EXIST_GROUP_NAME = "None";
    private final String NON_EXIST_GROUP_NAME = "POO";
    private final long NON_EXIST_USER_ID = -1;
    private final long EXIST_USER_ID = 0;
    private final String EXIST_USER_NAME = "existUsername";
    private final String NON_EXIST_USER_NAME = "nonExistUsername";

    private final String[] NON_VALID_PASSWORD_CASES = new String[] {
            "abcd", "abcdefghijklmnopqrstuw", ""
    };
    private final String[] NON_VALID_USER_NAME_CASES = new String[] {
            "abcd", "§정보현§", "qwertyuiopasdfgh", "selab dev", "; DROP TABLE USER;", "<html>HELLO!<html>", ""
    };


    @Autowired
    public UserServiceTests(UserService service) {
        this.service = service;
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
        //given
        UserGroup mockGroup = new UserGroup();
        mockGroup.setName(EXIST_GROUP_NAME);

        when(groupRepository.findByName(EXIST_GROUP_NAME)).thenReturn(Optional.of(mockGroup));

        //test
        service.addUser(TEST_USER_NAME, TEST_USER_PASSWORD, EXIST_GROUP_NAME);
    }

    @Test
    public void addUserWithGroupValidTest() {
        //given
        UserGroup mockGroup = new UserGroup();
        mockGroup.setName(EXIST_GROUP_NAME);

        when(groupRepository.findByName(EXIST_GROUP_NAME)).thenReturn(Optional.of(mockGroup));

        //test
        Assertions.assertThrows(Exception.class, () -> service.addUser(TEST_USER_NAME, TEST_USER_PASSWORD, NON_EXIST_GROUP_NAME));

        for (String nonValidUsername : NON_VALID_USER_NAME_CASES) {
            Assertions.assertThrows(Exception.class, () ->
                    service.addUser(nonValidUsername, TEST_USER_PASSWORD, EXIST_GROUP_NAME)
            );
        }

        for (String nonValidPassword : NON_VALID_PASSWORD_CASES) {
            Assertions.assertThrows(Exception.class, () ->
                service.addUser(TEST_USER_NAME, nonValidPassword, EXIST_GROUP_NAME)
            );
        }
    }

    @Test
    public void addUserTest() {
        //given
        UserGroup mockGroup = new UserGroup();
        mockGroup.setName(EXIST_GROUP_NAME);

        when(groupRepository.findByName(EXIST_GROUP_NAME)).thenReturn(Optional.of(mockGroup));

        //test
        service.addUser(TEST_USER_NAME, TEST_USER_PASSWORD);
    }

    @Test
    public void addUserValidTest() {
        //given
        UserGroup mockGroup = new UserGroup();
        mockGroup.setName(EXIST_GROUP_NAME);

        when(groupRepository.findByName(EXIST_GROUP_NAME)).thenReturn(Optional.of(mockGroup));

        //test
        for (String nonValidUsername : NON_VALID_USER_NAME_CASES) {
            Assertions.assertThrows(Exception.class, () ->
                service.addUser(nonValidUsername, TEST_USER_PASSWORD)
            );
        }

        for (String nonValidPassword : NON_VALID_PASSWORD_CASES) {
            Assertions.assertThrows(Exception.class, () ->
                service.addUser(TEST_USER_NAME, nonValidPassword)
            );
        }
    }

    @Test
    public void getUserTest() {
        //given
        UserGroup mockGroup = new UserGroup();
        mockGroup.setName(EXIST_GROUP_NAME);

        User mockUser = new User();
        mockUser.setUsername(EXIST_USER_NAME);
        mockUser.setId(EXIST_USER_ID);
        mockUser.setUserGroup(mockGroup);

        UserVO mockUserVO = new UserVO() {
            @Override
            public long getId() {
                return mockUser.getId();
            }

            @Override
            public String getUsername() {
                return mockUser.getUsername();
            }

            @Override
            public boolean isEnabled() {
                return mockUser.getEnabled();
            }

            @Override
            public boolean isAccountNonExpired() {
                return mockUser.getAccountNonExpired();
            }

            @Override
            public boolean isAccountNonLocked() {
                return mockUser.getAccountNonLocked();
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return mockUser.getCredentialsNonExpired();
            }

            @Override
            public String getGroupName() {
                return mockUser.getUserGroup().getName();
            }

            @Override
            public LocalDateTime getRegisterDate() {
                return mockUser.getRegisterDate();
            }
        };

        when(userRepository.findUserVoById(EXIST_USER_ID)).thenReturn(Optional.of(mockUserVO));

        //test
        Optional<UserVO> userVOOptional = service.getUser(EXIST_USER_ID);
        Assertions.assertTrue(userVOOptional.isPresent());
    }

    @Test
    public void isExistTest() {
        //given
        User existUser = new User();
        existUser.setId(EXIST_USER_ID);
        existUser.setUsername(EXIST_USER_NAME);
        when(userRepository.findByUsername(EXIST_USER_NAME)).thenReturn(Optional.of(existUser));
        when(userRepository.findByUsername(NON_EXIST_USER_NAME)).thenReturn(Optional.empty());

        //test
        Assertions.assertTrue(service.isExist(EXIST_USER_NAME));
        Assertions.assertFalse(service.isExist(NON_EXIST_USER_NAME));
    }

    @Test
    public void getAllUserListTest() {
        //given
        Pageable requestPageable = PageRequest.ofSize(10);
        when(userRepository.findAll(requestPageable)).thenReturn(Page.empty()); // XXX bhjung 빈 Page객체 대체할 mock Page 구현

        //test
        service.getAllUserList(requestPageable);
    }

    @Test
    public void updateTest() {
        //given
        UserGroup mockGroup = new UserGroup();
        mockGroup.setName(EXIST_GROUP_NAME);

        User mockUser = new User();
        mockUser.setUsername(EXIST_USER_NAME);
        mockUser.setId(EXIST_USER_ID);
        mockUser.setUserGroup(mockGroup);

        when(groupRepository.findByName(EXIST_GROUP_NAME)).thenReturn(Optional.of(mockGroup));
        when(userRepository.findById(EXIST_USER_ID)).thenReturn(Optional.of(mockUser));

        when(groupRepository.findByName(NON_EXIST_GROUP_NAME)).thenReturn(Optional.empty());
        when(userRepository.findById(NON_EXIST_USER_ID)).thenReturn(Optional.empty());

        //test
        service.update(EXIST_USER_ID, EXIST_GROUP_NAME);
    }

    @Test
    public void changePasswordTest() {
        //given

        //test
        service.changePassword(EXIST_USER_ID, TEST_USER_PASSWORD);
    }

    @Test
    public void changePasswordValidTest() {
        //given

        //test
        for (String nonValidPassword : NON_VALID_PASSWORD_CASES) {
            Assertions.assertThrows(Exception.class, () ->
                service.changePassword(EXIST_USER_ID, nonValidPassword)
            );
        }
    }

    @Test
    public void disableUserTest() {
        //given

        //test
        service.disableUser(EXIST_USER_ID);
    }

    @Test
    public void enableUserTest() {
        //given

        //test
        service.enableUser(EXIST_USER_ID);
    }

    @Test
    public void removeUserTest() {
        //given

        //test
        service.removeUser(EXIST_USER_ID);
    }

    @Test
    public void getUserGroupListTest() {
        //given
        UserGroup mockGroup = new UserGroup();
        mockGroup.setName(EXIST_GROUP_NAME);
        UserGroupVO mockGroupVO = new UserGroupVO() {
            @Override
            public long getId() {
                return mockGroup.getId();
            }

            @Override
            public String getName() {
                return mockGroup.getName();
            }
        };

        when(groupRepository.findAllBy()).thenReturn(Lists.list(mockGroupVO));

        //test
        List<UserGroupVO> groupVOS = service.getUserGroupList();
        Assertions.assertEquals(groupVOS.get(0).getName(), EXIST_GROUP_NAME);
    }

    @Test
    public void loadUserByUsernameTest() {
        //given
        UserGroupAuth mockAuth = new UserGroupAuth();
        mockAuth.setAuth(Auth.MEMBER);

        UserGroup mockGroup = new UserGroup();
        mockGroup.setName(EXIST_GROUP_NAME);
        mockGroup.setUserGroupAuthList(List.of(mockAuth));
        mockAuth.setUserGroup(mockGroup);

        User mockUser = new User();
        mockUser.setUsername(EXIST_USER_NAME);
        mockUser.setId(EXIST_USER_ID);
        mockUser.setUserGroup(mockGroup);
        mockUser.setEnabled(true);
        mockUser.setAccountNonExpired(true);
        mockUser.setAccountNonLocked(true);
        mockUser.setCredentialsNonExpired(true);

        when(userRepository.findByUsername(EXIST_USER_NAME)).thenReturn(Optional.of(mockUser));

        //test
        service.loadUserByUsername(EXIST_USER_NAME);
    }

    @Test
    public void loadUserByUsernameValidTest() {
        //given
        when(userRepository.findByUsername(NON_EXIST_USER_NAME)).thenReturn(Optional.empty());

        //test
        Assertions.assertThrows(Exception.class, () ->
            service.loadUserByUsername(NON_EXIST_USER_NAME)
        );
    }
}
