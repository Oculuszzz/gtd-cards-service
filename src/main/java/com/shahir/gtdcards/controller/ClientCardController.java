package com.shahir.gtdcards.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shahir.gtdcards.dto.ClientCardSchemeResponse;
import com.shahir.gtdcards.service.ClientCardService;

import lombok.RequiredArgsConstructor;

/**
 * REST controller exposing card-related APIs.
 */
@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
public class ClientCardController {

	private final ClientCardService clientCardService;

	/**
	 * Retrieves card scheme information for a specific client.
	 *
	 * @param clientId client identifier
	 * @return client card scheme details
	 */
	@GetMapping("/{clientId}/card-scheme")
	public ResponseEntity<ClientCardSchemeResponse> getClientCardScheme(@PathVariable Long clientId) {

		return ResponseEntity.ok(clientCardService.getClientCardScheme(clientId));
	}
}
