package com.swxy.jwbookms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JwBookMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwBookMsApplication.class, args);
    }

}

/**
 * spring-boot 会自动配置事务，相关的配置在 org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration
 */
