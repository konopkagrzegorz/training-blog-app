package com.training.trainingblogapp;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
@EnableEncryptableProperties
public class TrainingBlogAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainingBlogAppApplication.class, args);
	}

}
