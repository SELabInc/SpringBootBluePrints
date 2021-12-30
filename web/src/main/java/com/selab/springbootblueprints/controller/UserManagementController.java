package com.selab.springbootblueprints.controller;

import com.selab.springbootblueprints.model.entity.User;
import com.selab.springbootblueprints.model.entity.projection.UserGroupVO;
import com.selab.springbootblueprints.model.entity.projection.UserPageableInfoVO;
import com.selab.springbootblueprints.model.entity.projection.UserVO;
import com.selab.springbootblueprints.service.UserService;
import com.selab.springbootblueprints.exception.UserPasswordValidationException;
import com.selab.springbootblueprints.model.bean.Paginate;
import com.selab.springbootblueprints.model.bean.PostUserResponseStatus;
import com.selab.springbootblueprints.model.bean.UserUpdateDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/userManagement")
@SuppressWarnings("EmptyMethod")
@RequiredArgsConstructor
public class UserManagementController {

    private final UserService userService;

    @GetMapping("/table")
    public ModelAndView getUserTable( @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        ModelAndView modelAndView = new ModelAndView("userManagement/table");
        Page<UserPageableInfoVO> userPage = userService.getAllUserList(pageable);

        Paginate paginate = new Paginate(userPage.getNumber(), userPage.getTotalPages());

        modelAndView.addObject("userPage", userPage);
        modelAndView.addObject("paginate", paginate);

        return modelAndView;
    }

    @GetMapping("/modify/{id}")
    public ModelAndView getModify(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView("userManagement/modify");

        UserVO userVO = userService.getUser(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("user(id: %s) not exist", id))
        );

        List<UserGroupVO> userGroups = userService.getUserGroupList();

        modelAndView.addObject("user", userVO);
        modelAndView.addObject("userGroups", userGroups);

        return modelAndView;
    }

    @ResponseBody
    @GetMapping("/nameDuplicated")
    public ResponseEntity<PostUserResponseStatus> getNameDuplicated(@RequestParam(name = "name") String username) {
        PostUserResponseStatus result = PostUserResponseStatus.OK;

        if (!User.isValidName(username)) {
            result = PostUserResponseStatus.NAME_NOT_VALID;
        } else if (userService.isExist(username)) {
            result = PostUserResponseStatus.USERNAME_DUPLICATED;
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseBody
    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) {
        log.info(String.format("Delete user {id:%s}", id));
        userService.removeUser(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/user/{id}")
    public ResponseEntity<UserVO> getUser(@PathVariable long id) {
        UserVO user = userService.getUser(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("user(id: %s) not exist", id))
                );

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @ResponseBody
    @PutMapping("/user/{id}")
    public ResponseEntity<PostUserResponseStatus> putUser(@PathVariable long id, @Valid UserUpdateDTO dto) {
        PostUserResponseStatus result = PostUserResponseStatus.OK;

        String password = dto.getPassword();
        try {
            if (password != null && password.length() > 0) {
                    userService.changePassword(id, password);
            }
            userService.update(id, dto.getGroupName());
        } catch (UserPasswordValidationException e) {
            result = PostUserResponseStatus.PASSWORD_NOT_VALID;
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseBody
    @PutMapping("/user/{id}/password")
    public ResponseEntity<PostUserResponseStatus> putUserPassword(@PathVariable long id, String password) {
        PostUserResponseStatus result = PostUserResponseStatus.OK;

        try {
            userService.changePassword(id, password);
        } catch (UserPasswordValidationException e) {
            result = PostUserResponseStatus.PASSWORD_NOT_VALID;
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseBody
    @PutMapping("/enable/{id}")
    public ResponseEntity<Integer> putEnable(@PathVariable long id) {
        userService.enableUser(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ResponseBody
    @PutMapping("/disable/{id}")
    public ResponseEntity<Integer> putDisable(@PathVariable long id) {
        userService.disableUser(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}