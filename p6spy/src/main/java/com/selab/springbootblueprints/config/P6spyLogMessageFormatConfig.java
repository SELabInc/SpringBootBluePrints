package com.selab.springbootblueprints.config;

import com.p6spy.engine.spy.P6SpyOptions;
import com.selab.springbootblueprints.p6spy.CustomQueryFormatter;
import com.selab.springbootblueprints.p6spy.P6spySqlFormatConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;


@Configuration
public class P6spyLogMessageFormatConfig {

    @Bean
    public CustomQueryFormatter customQueryFormatter() {
        return CustomQueryFormatter.builder().build();
    }

    @PostConstruct
    public void setLogMessageFormat() {
        P6SpyOptions.getActiveInstance().setLogMessageFormat(P6spySqlFormatConfig.class.getName());
    }
}