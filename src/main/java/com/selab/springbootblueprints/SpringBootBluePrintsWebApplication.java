package com.selab.springbootblueprints;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;

@Slf4j
@EnableScheduling
@EnableAspectJAutoProxy
@SpringBootApplication
public class SpringBootBluePrintsWebApplication {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Value("${server.port}")
    private int serverPort;

    @PostConstruct
    public void started() throws Exception{

    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        if( activeProfile != "prod") {
            log.info("[{}] Starting local server.\n - http://localhost:{} \n - http://localhost:{}/swagger-ui.html", activeProfile, serverPort, serverPort);
        }
    }

    public static void main(String[] args) {

        SpringApplication.run(SpringBootBluePrintsWebApplication.class, args);
    }

    @Bean
    public ApplicationRunner getApplicationRunner() {
        return new ApplicationInitializer();
    }
}
