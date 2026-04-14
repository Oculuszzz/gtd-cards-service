package com.shahir.gtdcards.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.shahir.gtdcards.dto.ClientCardSchemeResponse;
import com.shahir.gtdcards.service.ClientCardService;

class ClientCardControllerTest {

	@Mock
	private ClientCardService clientCardService;

	@InjectMocks
	private ClientCardController clientCardController;

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
	void getClientCardScheme_ReturnsResponse() {
		Long clientId = 1L;
		ClientCardSchemeResponse.CardScheme cardScheme = ClientCardSchemeResponse.CardScheme.builder().scheme("VISA")
				.brand("BRAND").type("CREDIT").build();
		ClientCardSchemeResponse mockResponse = ClientCardSchemeResponse.builder().clientId(clientId)
				.clientName("Test Client").email("test@example.com").status("ACTIVE").cardScheme(cardScheme).build();
		when(clientCardService.getClientCardScheme(clientId)).thenReturn(mockResponse);

		ResponseEntity<ClientCardSchemeResponse> response = clientCardController.getClientCardScheme(clientId);

		assertNotNull(response);
		assertEquals(200, response.getStatusCode().value());
		assertEquals(mockResponse, response.getBody());
		verify(clientCardService, times(1)).getClientCardScheme(clientId);
	}
}
