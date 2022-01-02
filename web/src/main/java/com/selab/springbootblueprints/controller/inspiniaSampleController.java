package com.selab.springbootblueprints.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/inspinia")
public class inspiniaSampleController {

    @RequestMapping("/samplePage1")
    public String getSample1() {

        return "/inspinia/sample";
    }

    @RequestMapping("/samplePage2")
    public String getSample2() {

        return "/inspinia/home";
    }
}
