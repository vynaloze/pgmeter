package com.vynaloze.pgmeter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PgmeterApplication {

    public static void main(String[] args) {
        SpringApplication.run(PgmeterApplication.class, args);
    }

}
