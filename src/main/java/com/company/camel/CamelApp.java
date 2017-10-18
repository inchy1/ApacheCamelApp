package com.company.camel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CamelApp {

    private static final Logger logger = LoggerFactory.getLogger(CamelApp.class);

    public static void main(String[] args) {
        SpringApplication.run(CamelApp.class, args);
        logger.info("Camel app has started.");
    }
}
