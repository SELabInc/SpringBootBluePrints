package com.selab.springbootblueprints.controller;

import com.selab.springbootblueprints.exception.UserPasswordValidationException;
import com.selab.springbootblueprints.model.bean.UserGroupVO;
import com.selab.springbootblueprints.model.bean.UserVO;
import com.selab.springbootblueprints.model.entity.User;
import com.selab.springbootblueprints.service.UserService;
import com.selab.springbootblueprints.util.Paginate;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/userManagement")
@SuppressWarnings("EmptyMethod")
public class UserManagementController {

    @Setter(onMethod_ = @Autowired)
    private UserService userService;

    @GetMapping("/table")
    public void getUserTable(Model model,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "10") int size,
                             @RequestParam(defaultValue = "id") String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<UserVO> userPage = userService.getAllUserList(pageable);

        model.addAttribute("users", userPage.getContent());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("sort", sort);
        model.addAttribute("totalPage", userPage.getTotalPages());
        model.addAttribute("totalElement", userPage.getTotalElements());
        model.addAttribute("numberOfElements", userPage.getNumberOfElements());
        model.addAttribute("paginateStartNumber", Paginate.getPaginateStartNumber(page));
        model.addAttribute("paginateEndNumber", Paginate.getPaginateEndNumber(page, userPage.getTotalPages()));
    }

    @GetMapping("/modify/{id}")
    public String getModify(@PathVariable long id, Model model) {
        UserVO userVO = userService.getUser(id);
        List<UserGroupVO> userGroups = userService.getUserGroupList();

        model.addAttribute("user", userVO);
        model.addAttribute("userGroups", userGroups);

        return "userManagement/modify";
    }

    @ResponseBody
    @GetMapping("/overlap")
    public ResponseEntity<Integer> getOverlap(String name) {
        int resultValue = 0;

        if (!User.isValidName(name)) {
            resultValue = 1;
        } else if (userService.isExist(name)) {
            resultValue = 3;
        }

        return new ResponseEntity<>(resultValue, HttpStatus.OK);
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
        UserVO user = userService.getUser(id);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @ResponseBody
    @PutMapping("/user/{id}")
    public ResponseEntity<Integer> putUser(@PathVariable long id, String password, String groupName) {
        int resultValue = 0;

        try {
            if (password != null && password.length() > 0) {
                    userService.changePassword(id, password);
            }
            userService.update(id, groupName);
        } catch (UserPasswordValidationException e) {
            resultValue = 2;
        }

        return new ResponseEntity<>(resultValue, HttpStatus.OK);
    }

    @ResponseBody
    @PutMapping("/user/{id}/password")
    public ResponseEntity<Integer> putUserPassword(@PathVariable long id, String password) {    // XXX bhjung NOT USED
        int resultValue = 0;

        try {
            userService.changePassword(id, password);
        } catch (UserPasswordValidationException e) {
            resultValue = 2;
        }

        return new ResponseEntity<>(resultValue, HttpStatus.OK);
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

//    // TODO bhjung DB row not found exception handling
//    @ExceptionHandler({})
//    public ResponseEntity<Integer> handleNotFoundException(HttpServletRequest request, HttpServletResponse response) {
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
}