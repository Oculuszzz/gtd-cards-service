package com.shahir.gtdcards.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object representing a client response.
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ClientResponse {

	private Long id;
	private String name;
	private String email;
	private String status;
	private LocalDateTime createdAt;

}
