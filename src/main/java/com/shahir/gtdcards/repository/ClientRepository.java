package com.shahir.gtdcards.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shahir.gtdcards.entity.ClientEntity;

/**
 * Repository interface for {@link ClientEntity}.
 *
 * <p>
 * This repository provides database access operations for client data,
 * leveraging Spring Data JPA to reduce boilerplate code.
 * </p>
 */
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {

	/**
	 * Retrieves a client by its unique email address.
	 *
	 * @param email the email address to search for
	 * @return an {@link Optional} containing the matching client, or empty if no
	 *         client is found
	 */
	Optional<ClientEntity> findByEmail(String email);

	/**
	 * Determines whether a client exists using the given email.
	 *
	 * @param email email address
	 * @return true if exists, false otherwise
	 */
	boolean existsByEmail(String email);

}
