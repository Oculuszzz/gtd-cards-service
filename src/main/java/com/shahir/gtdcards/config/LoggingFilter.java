package com.shahir.gtdcards.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * Servlet filter responsible for logging HTTP request and response details.
 *
 */
@Component
@Slf4j
public class LoggingFilter extends OncePerRequestFilter {

	private static final String TRACE_ID_HEADER = "X-Trace-Id";
	private static final String MDC_TRACE_ID = "traceId";
	private static final String ACTUATOR_PATH_PREFIX = "/actuator";

	@Override
	protected void doFilterInternal(HttpServletRequest request, @Nonnull HttpServletResponse response,
			@Nonnull FilterChain filterChain) throws ServletException, IOException {

		// If actuator endpoint, skip logging
		if (isActuatorEndpoint(request.getRequestURI())) {
			filterChain.doFilter(request, response);
			return;
		}

		String traceId = resolveTraceId(request);
		MDC.put(MDC_TRACE_ID, traceId);
		response.setHeader(TRACE_ID_HEADER, traceId);

		ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request, 0); // Currently set 0 =
																									// no cache limit
		ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

		long startTime = System.currentTimeMillis();

		try {
			filterChain.doFilter(wrappedRequest, wrappedResponse);
		} finally {
			long duration = System.currentTimeMillis() - startTime;

			logRequest(wrappedRequest);
			logResponse(wrappedResponse, duration);

			wrappedResponse.copyBodyToResponse();
			MDC.clear(); // To prevent trace leakage
		}
	}

	/**
	 * Resolves trace ID from request header or generates a new one.
	 *
	 * @param request HTTP request
	 * @return trace ID
	 */
	private String resolveTraceId(HttpServletRequest request) {
		String traceId = request.getHeader(TRACE_ID_HEADER);
		return (traceId == null || traceId.isBlank()) ? UUID.randomUUID().toString() : traceId;
	}

	/**
	 * Checks if the request URI is for an actuator endpoint.
	 *
	 * @param uri the request URI
	 * @return true if it's an actuator endpoint, false otherwise
	 */
	private boolean isActuatorEndpoint(String uri) {
		return uri != null && uri.startsWith(ACTUATOR_PATH_PREFIX);
	}

	/**
	 * Logs the HTTP request details including method, URI, and body.
	 *
	 * @param request the wrapped HTTP request
	 */
	private void logRequest(ContentCachingRequestWrapper request) {
		String body = new String(request.getContentAsByteArray(), StandardCharsets.UTF_8);

		log.info("Incoming Request: | Method={} | URI={} | Body={}", request.getMethod(), request.getRequestURI(),
				body);
	}

	/**
	 * Logs the HTTP response details including status and body.
	 *
	 * @param response the wrapped HTTP response
	 * @param duration the time taken to process the request in milliseconds
	 */
	private void logResponse(ContentCachingResponseWrapper response, long duration) {

		String body = new String(response.getContentAsByteArray(), StandardCharsets.UTF_8);

		log.info("Response status: | Status={} | Duration={}ms | Body={}", response.getStatus(), duration, body);
	}
}
