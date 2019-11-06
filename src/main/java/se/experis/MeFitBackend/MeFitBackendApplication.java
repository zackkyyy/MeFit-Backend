package se.experis.MeFitBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class MeFitBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeFitBackendApplication.class, args);
	}
}
