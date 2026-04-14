package com.shahir.gtdcards.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shahir.gtdcards.dto.ClientResponse;
import com.shahir.gtdcards.dto.CreateClientRequest;
import com.shahir.gtdcards.dto.UpdateClientRequest;
import com.shahir.gtdcards.service.ClientService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * REST controller exposing client-related APIs.
 *
 */
@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
public class ClientController {

	private final ClientService clientService;

	/**
	 * Creates a new client.
	 *
	 * @param request validated client creation request
	 * @return created client details
	 */
	@PostMapping
	public ResponseEntity<ClientResponse> createClient(@Valid @RequestBody CreateClientRequest request) {

		ClientResponse response = clientService.createClient(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	/**
	 * Retrieves a paginated list of clients.
	 *
	 * @param page     page number (0-based, default = 0)
	 * @param pageSize number of records per page (default = 10)
	 * @return paginated list of clients
	 */
	@GetMapping
	public ResponseEntity<Page<ClientResponse>> getClients(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int pageSize) {

		return ResponseEntity.ok(clientService.getClients(page, pageSize));
	}

	/**
	 * Retrieves a client by ID.
	 *
	 * @param clientId unique client identifier
	 * @return client details
	 */
	@GetMapping("/{clientId}")
	public ResponseEntity<ClientResponse> getClientById(@PathVariable Long clientId) {

		return ResponseEntity.ok(clientService.getClientById(clientId));
	}

	/**
	 * Updates an existing client.
	 *
	 * @param clientId client identifier
	 * @param request  update payload
	 * @return updated client response
	 */
	@PutMapping("/{clientId}")
	public ResponseEntity<ClientResponse> updateClient(@PathVariable Long clientId,
			@Valid @RequestBody UpdateClientRequest request) {

		return ResponseEntity.ok(clientService.updateClient(clientId, request));
	}

}
