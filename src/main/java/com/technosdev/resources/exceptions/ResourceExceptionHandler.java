package com.technosdev.resources.exceptions;

import com.technosdev.services.exceptions.DatabaseException;
import com.technosdev.services.exceptions.ResourceNotFoundException;

import com.technosdev.services.exceptions.UnprocessableEntityException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
		List<String> errors = new ArrayList<>();
		errors.add("Resource not found");

		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(Instant.now(), status.value(), errors, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request) {
		List<String> errors = new ArrayList<>();
		errors.add("Database error");

		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(), status.value(), errors, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> handleValidationExceptions(MethodArgumentNotValidException e, HttpServletRequest request) {
		List<String> errors = new ArrayList<>();
		e.getBindingResult().getAllErrors().forEach((error) -> {
			String errorMessage = error.getDefaultMessage();
			errors.add(errorMessage);
		});

		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		StandardError err = new StandardError(Instant.now(), status.value(), errors, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(UnprocessableEntityException.class)
	public ResponseEntity<StandardError> handleUnprocessableEntityExceptions(UnprocessableEntityException e, HttpServletRequest request) {
		List<String> errors = new ArrayList<>();
		errors.add(e.getMessage());

		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		StandardError err = new StandardError(Instant.now(), status.value(), errors, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<StandardError> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e, HttpServletRequest request) {
		List<String> errors = new ArrayList<>();
		errors.add(e.getMessage());

		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		StandardError err = new StandardError(Instant.now(), status.value(), errors, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
}
