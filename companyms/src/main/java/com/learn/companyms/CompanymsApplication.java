package com.learn.companyms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.learn.companyms", "com.learn.common.exceptions"})
public class CompanymsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompanymsApplication.class, args);
	}

}
