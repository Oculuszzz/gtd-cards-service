package com.shahir.gtdcards.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

/**
 * OpenAPI configuration for GTD Cards assessment project.
 */
@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI gtdCardsOpenAPI() {
		return new OpenAPI().info(new Info().title("GTD Cards API").description("GTD Cards Spring Boot Assessment APIs")
				.version("1.0.0"));
	}
}
