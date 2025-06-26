package br.com.sistema.coworking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CoworkingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoworkingApplication.class, args);
	}
}
