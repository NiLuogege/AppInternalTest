package com.airent.appinternaltest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@MapperScan("com.airent.appinternaltest.mapper")
@PropertySource("application.properties")
public class AppinternaltestApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppinternaltestApplication.class, args);
	}
}
