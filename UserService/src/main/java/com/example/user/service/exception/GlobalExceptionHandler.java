package com.example.user.service.exception;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(UserException.class)
	public ResponseEntity<ErrorDetails> handleUserException(UserException ex, WebRequest request) {
		logger.error("UserException occurred: {}", ex.getMessage());
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDetails> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
			WebRequest request) {
		logger.error("MethodArgumentNotValidException occurred: {}", ex.getMessage());
		String errorMessage = ex.getBindingResult().getFieldError().getDefaultMessage();
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), "Validation Error", errorMessage);
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ErrorDetails> handleNoHandlerFoundException(NoHandlerFoundException ex, WebRequest request) {
		logger.error("NoHandlerFoundException occurred: {}", ex.getMessage());
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex, WebRequest request) {
		logger.error("Exception occurred: {}", ex.getMessage());
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), "Internal Server Error", ex.getMessage());
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
