package com.selab.springbootblueprints.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@SuppressWarnings("EmptyMethod")
public class LoginController {

    @GetMapping("/login")
    public void getSignIn() {

    }

    @GetMapping("/logout")
    public void getSignOut() {

    }

    @GetMapping("/accessDenied")
    public void getAccessDenied() {

    }

}
