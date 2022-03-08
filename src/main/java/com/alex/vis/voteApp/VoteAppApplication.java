package com.alex.vis.voteApp;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VoteAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(VoteAppApplication.class, args);
	}

	@Bean
	public OpenAPI openApiConfig() {
		return new OpenAPI().info(apiInfo());
	}

	public Info apiInfo() {
		Info info = new Info();

		info
				.title("Restaurant Voting App")
				.description("Application for customer voting what restaurant they will visit today")
				.version("v1.0.0");
		return info;
	}

}
