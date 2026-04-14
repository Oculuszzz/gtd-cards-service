package com.shahir.gtdcards.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateClientRequest {

	/**
	 * Full name of the client.
	 */
	@NotBlank(message = "Client name must not be blank")
	private String name;

	@NotBlank(message = "Client status must not be blank")
	private String status;

}
