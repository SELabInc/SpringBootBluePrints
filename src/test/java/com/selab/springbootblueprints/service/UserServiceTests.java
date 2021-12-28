package com.selab.springbootblueprints.service;

import com.selab.springbootblueprints.model.bean.Auth;
import com.selab.springbootblueprints.model.entity.QUser;
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

    private final String testUserName = "TESTUSER";
    private final String testUserPassword = "TESTPASSWORD";
    private final String existGroupName = "None";
    private final String nonExistGroupName = "POO";
    private final long nonExistUserId = -1;
    private final long existUserId = 0;
    private final String existUsername = "existUsername";
    private final String nonExistUsername = "nonExistUsername";

    private final String[] nonValidPasswordCases = new String[] {
            "abcd", "abcdefghijklmnopqrstuw", ""
    };
    private final String[] nonValidUsernameCases = new String[] {
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
        mockGroup.setName(existGroupName);

        when(groupRepository.findByName(existGroupName)).thenReturn(Optional.of(mockGroup));

        //test
        service.addUser(testUserName, testUserPassword, existGroupName);
    }

    @Test
    public void addUserWithGroupValidTest() {
        //given
        UserGroup mockGroup = new UserGroup();
        mockGroup.setName(existGroupName);

        when(groupRepository.findByName(existGroupName)).thenReturn(Optional.of(mockGroup));

        //test
        Assertions.assertThrows(Exception.class, () -> service.addUser(testUserName, testUserPassword, nonExistGroupName));

        for (String nonValidUsername : nonValidUsernameCases) {
            Assertions.assertThrows(Exception.class, () ->
                    service.addUser(nonValidUsername, testUserPassword, existGroupName)
            );
        }

        for (String nonValidPassword : nonValidPasswordCases) {
            Assertions.assertThrows(Exception.class, () ->
                service.addUser(testUserName, nonValidPassword, existGroupName)
            );
        }
    }

    @Test
    public void addUserTest() {
        //given
        UserGroup mockGroup = new UserGroup();
        mockGroup.setName(existGroupName);

        when(groupRepository.findByName(existGroupName)).thenReturn(Optional.of(mockGroup));

        //test
        service.addUser(testUserName, testUserPassword);
    }

    @Test
    public void addUserValidTest() {
        //given
        UserGroup mockGroup = new UserGroup();
        mockGroup.setName(existGroupName);

        when(groupRepository.findByName(existGroupName)).thenReturn(Optional.of(mockGroup));

        //test
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
        //given
        UserGroup mockGroup = new UserGroup();
        mockGroup.setName(existGroupName);

        User mockUser = new User();
        mockUser.setUsername(existUsername);
        mockUser.setId(existUserId);
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

        when(userRepository.findUserVoById(existUserId)).thenReturn(Optional.of(mockUserVO));

        //test
        Optional<UserVO> userVOOptional = service.getUser(existUserId);
        Assertions.assertTrue(userVOOptional.isPresent());
    }

    @Test
    public void isExistTest() {
        //given
        when(userRepository.exists(QUser.user.username.eq(existUsername))).thenReturn(true);
        when(userRepository.exists(QUser.user.username.eq(nonExistUsername))).thenReturn(false);

        //test
        Assertions.assertTrue(service.isExist(existUsername));
        Assertions.assertFalse(service.isExist(nonExistUsername));
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
        mockGroup.setName(existGroupName);

        User mockUser = new User();
        mockUser.setUsername(existUsername);
        mockUser.setId(existUserId);
        mockUser.setUserGroup(mockGroup);

        when(groupRepository.findByName(existGroupName)).thenReturn(Optional.of(mockGroup));
        when(userRepository.findById(existUserId)).thenReturn(Optional.of(mockUser));

        when(groupRepository.findByName(nonExistGroupName)).thenReturn(Optional.empty());
        when(userRepository.findById(nonExistUserId)).thenReturn(Optional.empty());

        //test
        service.update(existUserId, existGroupName);
    }

    @Test
    public void changePasswordTest() {
        //given

        //test
        service.changePassword(existUserId, testUserPassword);
    }

    @Test
    public void changePasswordValidTest() {
        //given

        //test
        for (String nonValidPassword : nonValidPasswordCases) {
            Assertions.assertThrows(Exception.class, () ->
                service.changePassword(existUserId, nonValidPassword)
            );
        }
    }

    @Test
    public void disableUserTest() {
        //given

        //test
        service.disableUser(existUserId);
    }

    @Test
    public void enableUserTest() {
        //given

        //test
        service.enableUser(existUserId);
    }

    @Test
    public void removeUserTest() {
        //given

        //test
        service.removeUser(existUserId);
    }

    @Test
    public void getUserGroupListTest() {
        //given
        UserGroup mockGroup = new UserGroup();
        mockGroup.setName(existGroupName);
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
        Assertions.assertEquals(groupVOS.get(0).getName(), existGroupName);
    }

    @Test
    public void loadUserByUsernameTest() {
        //given
        UserGroupAuth mockAuth = new UserGroupAuth();
        mockAuth.setAuth(Auth.MEMBER);

        UserGroup mockGroup = new UserGroup();
        mockGroup.setName(existGroupName);
        mockGroup.setUserGroupAuthList(List.of(mockAuth));
        mockAuth.setUserGroup(mockGroup);

        User mockUser = new User();
        mockUser.setUsername(existUsername);
        mockUser.setId(existUserId);
        mockUser.setUserGroup(mockGroup);
        mockUser.setEnabled(true);
        mockUser.setAccountNonExpired(true);
        mockUser.setAccountNonLocked(true);
        mockUser.setCredentialsNonExpired(true);

        when(userRepository.findByUsername(existUsername)).thenReturn(Optional.of(mockUser));

        //test
        service.loadUserByUsername(existUsername);
    }

    @Test
    public void loadUserByUsernameValidTest() {
        //given
        when(userRepository.findByUsername(nonExistUsername)).thenReturn(Optional.empty());

        //test
        Assertions.assertThrows(Exception.class, () ->
            service.loadUserByUsername(nonExistUsername)
        );
    }
}
