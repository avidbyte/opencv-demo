package com.example.opencvdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class OpencvDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpencvDemoApplication.class, args);
    }

}
