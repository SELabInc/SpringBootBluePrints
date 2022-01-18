package com.selab.webexample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

@Slf4j
public class ApplicationInitializer implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("========== APPLICATION INITIALIZE ==========");

    }
}