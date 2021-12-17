package com.selab.springbootblueprints.controller;

import com.selab.springbootblueprints.exception.UserNameValidationException;
import com.selab.springbootblueprints.exception.UserPasswordValidationException;
import com.selab.springbootblueprints.model.bean.PostUserResponseStatus;
import com.selab.springbootblueprints.service.UserService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class IndexController {

    @Setter(onMethod_ = @Autowired)
    private UserService userService;

    @RequestMapping("/")
    public String getRoot() {

        return "index";
    }

    @GetMapping("/register")
    public void getSignUp() {

    }

    @ResponseBody
    @PostMapping("/register")
    public ResponseEntity<PostUserResponseStatus> postUser(String username, String password) {
        PostUserResponseStatus resultValue = PostUserResponseStatus.OK;

        try {
            userService.addUser(username, password);
        } catch (UserNameValidationException e) {
            resultValue = PostUserResponseStatus.NAME_NOT_VALID;
        } catch (UserPasswordValidationException e) {
            resultValue = PostUserResponseStatus.PASSWORD_NOT_VALID;
        }

        return resultValue.equals(PostUserResponseStatus.OK) ?
                new ResponseEntity<>(resultValue, HttpStatus.CREATED) : new ResponseEntity<>(resultValue, HttpStatus.OK);
    }
}
