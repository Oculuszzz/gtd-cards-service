package com.shahir.gtdcards.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration for REST clients used to communicate with external systems.
 */
@Configuration
public class RestClientConfig {

	/**
	 * Provides a {@link RestTemplate} for outbound HTTP calls.
	 *
	 * @return configured RestTemplate
	 */
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
