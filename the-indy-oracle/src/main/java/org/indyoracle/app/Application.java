package org.indyoracle.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@ComponentScan(basePackages = {"org.indyoracle.controllers"})
public class Application {
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}