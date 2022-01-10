package com.selab.springbootblueprints.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SuppressWarnings("EmptyMethod")
public class LoginController {

    @Value("${network.area}")
    private String resourceType;

    @GetMapping("/login")
    public ModelAndView getSignIn(boolean fail) {
        ModelAndView modelAndView = new ModelAndView("login");
        if (fail) {
            modelAndView.addObject("message", "incorrect username or password.");
        }
        modelAndView.addObject("resourceType", resourceType);

        return modelAndView;
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
