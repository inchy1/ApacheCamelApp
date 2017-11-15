package com.company.camel.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@ComponentScan(value = {"com.company.camel"})
@EnableJms
public class CamelApp {

    private static final Logger logger = LoggerFactory.getLogger(CamelApp.class);

    public static void main(String[] args) {
        SpringApplication.run(CamelApp.class, args);
        logger.info("Camel app has started.");
    }
}
