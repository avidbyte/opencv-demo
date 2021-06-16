package com.example.opencvdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author aaron
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class OpenCvDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenCvDemoApplication.class, args);
    }

}
