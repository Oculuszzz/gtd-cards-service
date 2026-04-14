package com.shahir.gtdcards.service;

import org.springframework.data.domain.Page;

import com.shahir.gtdcards.dto.ClientResponse;
import com.shahir.gtdcards.dto.CreateClientRequest;
import com.shahir.gtdcards.dto.UpdateClientRequest;

/**
 * Service interface defining business operations related to clients.
 *
 */
public interface ClientService {

	/**
	 * Creates a new client based on the provided request data.
	 *
	 * @param request the client creation request
	 * @return the created client response
	 */
	ClientResponse createClient(CreateClientRequest request);

	/**
	 * Retrieves clients in a paginated manner.
	 *
	 * @param page     page number (0-based)
	 * @param pageSize number of records per page
	 * @return paginated list of clients
	 */
	Page<ClientResponse> getClients(int page, int pageSize);

	/**
	 * Retrieves a client by its unique identifier.
	 *
	 * @param clientId client identifier
	 * @return client details
	 */
	ClientResponse getClientById(Long clientId);

	/**
	 * Updates an existing client.
	 *
	 * @param clientId client identifier
	 * @param request  update request payload
	 * @return updated client information
	 */
	ClientResponse updateClient(Long clientId, UpdateClientRequest request);

}
