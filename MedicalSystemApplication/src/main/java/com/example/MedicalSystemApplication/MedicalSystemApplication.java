package com.example.MedicalSystemApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


import org.springframework.scheduling.annotation.EnableScheduling;

@EntityScan("model")
@EnableJpaRepositories(basePackages="repository")
@SpringBootApplication(scanBasePackages = {"model","service","repository","controller","org.nil","config"})
@EnableScheduling
public class MedicalSystemApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(MedicalSystemApplication.class, args);
	}

}
