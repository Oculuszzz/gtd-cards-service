package com.shahir.gtdcards.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object representing a client creation request.
 *
 */
@Getter
@Setter
public class CreateClientRequest {

	/**
	 * Full name of the client.
	 */
	@NotBlank(message = "Client name must not be blank")
	private String name;

	/**
	 * Email address of the client.
	 */
	@NotBlank(message = "Email must not be blank")
	@Email(message = "Email must be a valid email address")
	private String email;

}
