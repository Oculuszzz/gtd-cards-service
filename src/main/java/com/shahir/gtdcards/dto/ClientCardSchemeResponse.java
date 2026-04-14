package com.shahir.gtdcards.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * DTO representing a client card scheme response.
 *
 */
@Getter
@Builder
public class ClientCardSchemeResponse {

	private Long clientId;
	private String clientName;
	private String email;
	private String status;

	private CardScheme cardScheme;

	/**
	 * Nested DTO representing card scheme details.
	 */
	@Getter
	@Builder
	public static class CardScheme {
		private String scheme;
		private String brand;
		private String type;
	}
}
