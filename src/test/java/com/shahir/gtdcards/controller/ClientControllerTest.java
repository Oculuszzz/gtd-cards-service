package com.shahir.gtdcards.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.shahir.gtdcards.dto.ClientResponse;
import com.shahir.gtdcards.dto.CreateClientRequest;
import com.shahir.gtdcards.dto.UpdateClientRequest;
import com.shahir.gtdcards.service.ClientService;

class ClientControllerTest {

	@Mock
	private ClientService clientService;

	@InjectMocks
	private ClientController clientController;

	private AutoCloseable closeable;

	@BeforeEach
	void setUp() {
		closeable = MockitoAnnotations.openMocks(this);
	}

	@org.junit.jupiter.api.AfterEach
	void tearDown() throws Exception {
		closeable.close();
	}

	@Test
	void createClient_ReturnsCreatedClient() {
		CreateClientRequest request = mock(CreateClientRequest.class);
		ClientResponse response = mock(ClientResponse.class);
		when(clientService.createClient(request)).thenReturn(response);

		ResponseEntity<ClientResponse> result = clientController.createClient(request);
		assertEquals(HttpStatus.CREATED, result.getStatusCode());
		assertEquals(response, result.getBody());
		verify(clientService, times(1)).createClient(request);
	}

	@Test
	void getClients_ReturnsPageOfClients() {
		@SuppressWarnings("unchecked")
		Page<ClientResponse> page = mock(Page.class);
		when(clientService.getClients(0, 10)).thenReturn(page);

		ResponseEntity<Page<ClientResponse>> result = clientController.getClients(0, 10);
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertEquals(page, result.getBody());
		verify(clientService, times(1)).getClients(0, 10);
	}

	@Test
	void getClientById_ReturnsClient() {
		Long clientId = 1L;
		ClientResponse response = mock(ClientResponse.class);
		when(clientService.getClientById(clientId)).thenReturn(response);

		ResponseEntity<ClientResponse> result = clientController.getClientById(clientId);
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertEquals(response, result.getBody());
		verify(clientService, times(1)).getClientById(clientId);
	}

	@Test
	void updateClient_ReturnsUpdatedClient() {
		Long clientId = 1L;
		UpdateClientRequest request = mock(UpdateClientRequest.class);
		ClientResponse response = mock(ClientResponse.class);
		when(clientService.updateClient(clientId, request)).thenReturn(response);

		ResponseEntity<ClientResponse> result = clientController.updateClient(clientId, request);
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertEquals(response, result.getBody());
		verify(clientService, times(1)).updateClient(clientId, request);
	}
}
