package com.learn.jobms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication(scanBasePackages = {"com.learn.jobms", "com.learn.common.exceptions"})
public class JobmsApplication {

    static void main(String[] args) {
        SpringApplication.run(JobmsApplication.class, args);
    }

}
