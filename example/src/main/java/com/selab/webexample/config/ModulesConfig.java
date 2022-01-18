package com.selab.webexample.config;

import com.selab.springbootblueprints.p6spy.P6spyWizard;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModulesConfig {

    @Bean
    public P6spyWizard p6spyWizard() {
        return P6spyWizard.builder().build();
    }
}
