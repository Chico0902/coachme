package com.ssafy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
//@ComponentScan(basePackages = "com.ssafy")
public class CoachMeApplication {

  public static void main(String[] args) {
    SpringApplication.run(CoachMeApplication.class, args);
  }
}
