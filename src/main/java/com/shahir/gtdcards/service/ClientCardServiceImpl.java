package com.shahir.gtdcards.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.shahir.gtdcards.dto.ClientCardSchemeResponse;
import com.shahir.gtdcards.dto.external.ExternalCardSchemeResponse;
import com.shahir.gtdcards.entity.ClientEntity;
import com.shahir.gtdcards.repository.ClientRepository;

import lombok.RequiredArgsConstructor;

/**
 * Implementation of {@link ClientCardService}.
 *
 * <p>
 * Handles card scheme enrichment by invoking an external card classification
 * service.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class ClientCardServiceImpl implements ClientCardService {

	/**
	 * binlist.net is a public web service for looking up credit and debit card
	 * metadata. (Used as a mock external card service for assessment)
	 */
	private static final String CARD_SCHEME_LOOKUP_URL = "https://lookup.binlist.net/{bin}";

	private final ClientRepository clientRepository;
	private final RestTemplate restTemplate;

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public ClientCardSchemeResponse getClientCardScheme(Long clientId) {

		ClientEntity client = clientRepository.findById(clientId)
				.orElseThrow(() -> new IllegalArgumentException("Client not found with id: " + clientId));

		// This is a mock implementation for demonstration purposes.
		// The real banking application would have actual card data associated with the
		// client,
		// Mock BIN (Card number) value for demonstration
		String sampleBin = "45717360";

		ExternalCardSchemeResponse externalResponse = restTemplate.getForObject(CARD_SCHEME_LOOKUP_URL,
				ExternalCardSchemeResponse.class, sampleBin);

		return buildResponse(client, externalResponse);
	}

	/**
	 * Builds a combined response using internal client data and external card
	 * scheme data.
	 *
	 * @param client   internal client entity
	 * @param external external card scheme data
	 * @return combined card scheme response
	 */
	private ClientCardSchemeResponse buildResponse(ClientEntity client, ExternalCardSchemeResponse external) {

		return ClientCardSchemeResponse.builder().clientId(client.getId()).clientName(client.getName())
				.email(client.getEmail()).status(client.getStatus().name())
				.cardScheme(ClientCardSchemeResponse.CardScheme.builder()
						.scheme(external != null ? external.getScheme() : null)
						.brand(external != null ? external.getBrand() : null)
						.type(external != null ? external.getType() : null).build())
				.build();
	}
}
