package com.ServiceRegistry.Registar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class RegistarApplication {
	public static void main(String[] args) {
		SpringApplication.run(RegistarApplication.class, args);
	}

}
