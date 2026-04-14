package com.shahir.gtdcards.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

/**
 * Global exception handler for REST APIs.
 *
 * <p>
 * Centralizes exception handling and ensures consistent error responses across
 * all endpoints.
 * </p>
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	/**
	 * Handles validation errors triggered by {@code @Valid}.
	 *
	 * @param ex validation exception
	 * @return structured error response
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {

		Map<String, String> fieldErrors = new HashMap<>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			fieldErrors.put(error.getField(), error.getDefaultMessage());
		}

		Map<String, Object> response = new HashMap<>();
		response.put("timestamp", LocalDateTime.now());
		response.put("status", HttpStatus.BAD_REQUEST.value());
		response.put("errors", fieldErrors);

		return ResponseEntity.badRequest().body(response);
	}

	/**
	 * Handles business exception when a client already exists.
	 *
	 * @param ex client already exists exception
	 * @return conflict response
	 */
	@ExceptionHandler(ClientAlreadyExistsException.class)
	public ResponseEntity<Map<String, Object>> handleClientAlreadyExistsException(ClientAlreadyExistsException ex) {

		log.warn("Client creation failed: {}", ex.getMessage());

		Map<String, Object> response = new HashMap<>();
		response.put("timestamp", LocalDateTime.now());
		response.put("status", HttpStatus.CONFLICT.value());
		response.put("message", ex.getMessage());

		return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
	}

	/**
	 * Handles all unexpected exceptions.
	 *
	 * @param ex uncaught exception
	 * @return internal server error response
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {

		log.error("Unexpected system error", ex);

		Map<String, Object> response = new HashMap<>();
		response.put("timestamp", LocalDateTime.now());
		response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		response.put("message", "Internal server error");

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}
}
