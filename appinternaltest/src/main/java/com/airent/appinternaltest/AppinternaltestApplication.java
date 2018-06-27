package com.airent.appinternaltest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.airent.appinternaltest.mapper")
public class AppinternaltestApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppinternaltestApplication.class, args);
	}
}
