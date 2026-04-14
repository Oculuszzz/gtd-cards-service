package com.shahir.gtdcards.exception;

/**
 * Exception thrown when attempting to create a client with an email address
 * that already exists.
 */
public class ClientAlreadyExistsException extends RuntimeException {

	public ClientAlreadyExistsException(String message) {
		super(message);
	}
}
