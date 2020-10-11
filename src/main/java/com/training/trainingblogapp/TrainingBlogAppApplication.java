package com.training.trainingblogapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
public class TrainingBlogAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainingBlogAppApplication.class, args);
	}

}
