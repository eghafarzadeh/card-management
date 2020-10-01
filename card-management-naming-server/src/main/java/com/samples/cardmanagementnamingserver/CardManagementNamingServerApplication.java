package com.samples.cardmanagementnamingserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@SpringBootApplication
@EnableEurekaServer
public class CardManagementNamingServerApplication {

    public static void main(String[] args) {

        SpringApplication.run(CardManagementNamingServerApplication.class, args);
    }

}
