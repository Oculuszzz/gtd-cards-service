package com.shahir.gtdcards.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shahir.gtdcards.dto.ClientResponse;
import com.shahir.gtdcards.dto.CreateClientRequest;
import com.shahir.gtdcards.dto.UpdateClientRequest;
import com.shahir.gtdcards.entity.ClientEntity;
import com.shahir.gtdcards.entity.ClientStatus;
import com.shahir.gtdcards.exception.ClientAlreadyExistsException;
import com.shahir.gtdcards.repository.ClientRepository;

import lombok.RequiredArgsConstructor;

/**
 * Implementation of {@link ClientService}.
 *
 */
@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

	private static final int MAX_PAGE_SIZE = 100;
	private static final int DEFAULT_PAGE_SIZE = 10;

	private final ClientRepository clientRepository;

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * This operation is transactional to ensure atomicity during client creation.
	 * </p>
	 */
	@Override
	@Transactional
	public ClientResponse createClient(CreateClientRequest request) {

		if (clientRepository.existsByEmail(request.getEmail())) {
			throw new ClientAlreadyExistsException("Client with email already exists: " + request.getEmail());
		}

		ClientEntity clientEntity = ClientEntity.builder().name(request.getName()).email(request.getEmail())
				.status(ClientStatus.ACTIVE).build();

		ClientEntity savedClient = clientRepository.save(clientEntity);

		return mapToResponse(savedClient);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<ClientResponse> getClients(int page, int pageSize) {

		int validatedPageSize = validatePageSize(pageSize);

		return clientRepository.findAll(PageRequest.of(page, validatedPageSize)).map(this::mapToResponse);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public ClientResponse getClientById(Long clientId) {
		ClientEntity entity = clientRepository.findById(clientId)
				.orElseThrow(() -> new IllegalArgumentException("Client not found with id: " + clientId));

		return mapToResponse(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public ClientResponse updateClient(Long clientId, UpdateClientRequest request) {

		ClientEntity client = clientRepository.findById(clientId)
				.orElseThrow(() -> new IllegalArgumentException("Client not found with id: " + clientId));

		client.setName(request.getName());
		client.setStatus(ClientStatus.valueOf(request.getStatus().toUpperCase()));

		ClientEntity updatedClient = clientRepository.save(client);

		return mapToResponse(updatedClient);
	}

	/**
	 * Applies validation rules for page size to avoid excessive data fetching.
	 *
	 * @param pageSize requested page size
	 * @return validated page size
	 */
	private int validatePageSize(int pageSize) {
		if (pageSize <= 0) {
			return DEFAULT_PAGE_SIZE;
		}
		return Math.min(pageSize, MAX_PAGE_SIZE);
	}

	/**
	 * Maps a {@link ClientEntity} to {@link ClientResponse}.
	 *
	 * @param entity client entity
	 * @return client response
	 */
	private ClientResponse mapToResponse(ClientEntity entity) {
		return ClientResponse.builder().id(entity.getId()).name(entity.getName()).email(entity.getEmail())
				.status(entity.getStatus().name()).createdAt(entity.getCreatedAt()).build();
	}

}
