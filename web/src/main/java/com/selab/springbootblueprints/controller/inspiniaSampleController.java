package com.selab.springbootblueprints.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/inspinia")
public class inspiniaSampleController {

    @Value("${network.area}")
    private String resourceType;

    @RequestMapping("/samplePage1")
    public String getSample1(Model model) {

        model.addAttribute("resourceType",resourceType);
        return "inspinia/samplePage1";
    }

    @RequestMapping("/samplePage2")
    public String getSample2(Model model) {

        model.addAttribute("resourceType",resourceType);
        return "inspinia/samplePage2";
    }

    @RequestMapping("/login")
    public String getSampleLogin() {

        return "inspinia/login";
    }

}
