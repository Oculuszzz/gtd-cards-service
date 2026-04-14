package com.shahir.gtdcards.dto.external;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO representing card scheme data returned from an external card
 * classification service.
 *
 */
@Getter
@Setter
public class ExternalCardSchemeResponse {

	/**
	 * Card scheme type (e.g., VISA, MASTERCARD).
	 */
	private String scheme;

	/**
	 * Card brand classification.
	 */
	private String brand;

	/**
	 * Indicates whether prepaid cards are supported.
	 */
	private String type;
}
