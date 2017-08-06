package com.boot.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Hello world!
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class BootPropertiesApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootPropertiesApplication.class, args);
    }
}
