package com.example.demo;

import com.example.demo.services.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		UserService.initDatabase();
		SpringApplication.run(DemoApplication.class, args);
	}

}
