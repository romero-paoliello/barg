package com.barganha.barganha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
//        SpringApplication app = new SpringApplication(Application.class);
//        app.setShowBanner(false);
//        app.run(args);

        ApplicationContext ctx = SpringApplication.run(Application.class, args);
    }

}
