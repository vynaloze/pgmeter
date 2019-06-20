package com.vynaloze.pgmeter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.tools.agent.ReactorDebugAgent;

@SpringBootApplication
public class PgmeterApplication {

    public static void main(String[] args) {
        ReactorDebugAgent.init();
        SpringApplication.run(PgmeterApplication.class, args);
    }

}
