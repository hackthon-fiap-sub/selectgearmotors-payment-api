package br.com.postech.sevenfoodpay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Run {

    public static void main(String[] args) {
        SpringApplication.run(Run.class, args);
    }

}