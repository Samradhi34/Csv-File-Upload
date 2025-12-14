package com.project.exception;

import java.nio.file.AccessDeniedException;
import java.util.stream.Collectors;

import javax.security.sasl.AuthenticationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.project.locale.MessageByLocaleService;
import com.project.response.GenericResponseHandlers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalHandlerExceptionController {

	private final MessageByLocaleService messageByLocaleService;

	/**
	 * Central exception handler and generate common custom response
	 *
	 * @param request
	 * @param exception
	 * @return
	 */

	@ExceptionHandler(Throwable.class)
	ResponseEntity<?> handleControllerException(final HttpServletRequest request, final Throwable exception) {
		HttpStatus status = null;
		String message = null;
		
		log.info("*****Global Exception Handler*****");

		if (exception instanceof ResourceNotFoundException) {
			status = HttpStatus.OK;
			message = exception.getMessage();
		} else if (exception instanceof BaseException baseException) {
			status = baseException.getStatus();
			message = baseException.getMessage();
		} else if (exception instanceof BaseRuntimeException baseRuntimeException) {
			status = baseRuntimeException.getStatus();
			message = baseRuntimeException.getMessage();
		} else if (exception instanceof MethodArgumentNotValidException validationException) {
			status = HttpStatus.BAD_REQUEST;
			message = validationException.getFieldErrors().stream().map(FieldError::getDefaultMessage)
					.collect(Collectors.joining());
		} else if (exception instanceof AccessDeniedException || exception instanceof AuthenticationException) {
			status = HttpStatus.UNAUTHORIZED;
			message = exception.getMessage();
		} else if (exception instanceof MissingServletRequestParameterException
				|| exception instanceof MissingRequestHeaderException
				|| exception instanceof HttpMessageNotReadableException) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			message = exception.getMessage();
		} else if (exception instanceof HttpClientErrorException || exception instanceof HttpServerErrorException) {
			if (exception.getMessage().contains("401")) {
				status = HttpStatus.UNAUTHORIZED;
				message = messageByLocaleService.getMessage("invalid.username.password", null);
			} else {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
				message = exception.getMessage();
			}
		} else if (exception instanceof ValidationException validationException) {
			status = HttpStatus.OK;
			message = validationException.getMessage();
		} else if (exception instanceof MethodArgumentTypeMismatchException) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			message = "Argument mis matched";
		} else {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			message = messageByLocaleService.getMessage("common.error", null);
			StringBuffer requestedURL = request.getRequestURL();
			log.info("Requested URL:{}", requestedURL);
			log.error("exception : {}", exception);
			
//			status = HttpStatus.INTERNAL_SERVER_ERROR;
//			message = (exception.getMessage() != null) ? exception.getMessage()
//					: "Something went wrong. Please try again.";
		}

		return new GenericResponseHandlers.Builder().setStatus(status).setMessage(message).create();
	}

}
