package com.selab.springbootblueprints;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;

@EnableScheduling
@EnableAspectJAutoProxy
@SpringBootApplication
public class SpringBootBluePrintsWebApplication {

    @PostConstruct
    public void started() throws Exception{

    }

    public static void main(String[] args) {

        SpringApplication.run(SpringBootBluePrintsWebApplication.class, args);
    }

    @Bean
    public ApplicationRunner getApplicationRunner() {
        return new ApplicationInitializer();
    }
}
