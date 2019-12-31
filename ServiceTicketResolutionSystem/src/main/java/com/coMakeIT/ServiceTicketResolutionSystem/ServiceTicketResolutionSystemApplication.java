package com.coMakeIT.ServiceTicketResolutionSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = { "business", "service", "com.coMakeIT.ServiceTicketResolutionSystem" , "controller", "rest"})
@EntityScan(basePackages = { "bean" })
@EnableJpaRepositories(basePackages = { "repository" })
public class ServiceTicketResolutionSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceTicketResolutionSystemApplication.class, args);
	}

}
