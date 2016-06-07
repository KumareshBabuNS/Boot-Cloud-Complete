package io.pivotal.boot.samples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CompaniesServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompaniesServiceApplication.class, args);
	}
}
