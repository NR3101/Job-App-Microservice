package com.learn.reviewms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.learn.reviewms", "com.learn.common.exceptions"})
public class ReviewmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReviewmsApplication.class, args);
	}

}
