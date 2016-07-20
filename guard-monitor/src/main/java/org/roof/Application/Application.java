package org.roof.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class Application {
	public static void main(String[] args) {
		Resource resource = new ClassPathResource("spring.xml");
		SpringApplication.run(resource, args);
	}
}
