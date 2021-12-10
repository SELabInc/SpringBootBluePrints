package com.selab.springbootblueprints.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@SuppressWarnings("EmptyMethod")
public class LoginController {

    @GetMapping("/login")
    public void getSignIn(Model model, boolean fail) {
        if (fail) {
            model.addAttribute("message", "incorrect username or password.");
        }
    }

    @GetMapping("/logout")
    public void getSignOut() {

    }

    @PostMapping("/loginFail")
    public String postLoginFail() {
        return "redirect:/login?fail=true";
    }

    @GetMapping("/accessDenied")
    public void getAccessDenied() {

    }

}
