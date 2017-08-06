package com.boot.study;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class BootLoggingApplication {

    //都是能打出来的
    private static final Log LOG_COMMON = LogFactory.getLog(BootLoggingApplication.class);
    private static final Logger LOG_LOG4J = LoggerFactory.getLogger(BootLoggingApplication.class);


    public static void main(String[] args) {
        LOG_COMMON.debug("common log 看一看不同的日志实现类能不能都打出来");
        LOG_LOG4J.debug("log4j 看一看不同的日志实现类能不能都打出来");
        SpringApplication.run(BootLoggingApplication.class, args);
    }
}
