package com.shahir.gtdcards.service;

import com.shahir.gtdcards.dto.ClientCardSchemeResponse;

/**
 * Service interface defining card-related operations.
 */
public interface ClientCardService {

	/**
	 * Retrieves card scheme information for a client by calling an external card
	 * classification service.
	 *
	 * @param clientId client identifier
	 * @return client card scheme response
	 */
	ClientCardSchemeResponse getClientCardScheme(Long clientId);
}
