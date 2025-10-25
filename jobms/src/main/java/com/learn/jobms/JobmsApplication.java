package com.learn.jobms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.learn.jobms", "com.learn.common.exceptions"})
public class JobmsApplication {

    static void main(String[] args) {
        SpringApplication.run(JobmsApplication.class, args);
    }

}
