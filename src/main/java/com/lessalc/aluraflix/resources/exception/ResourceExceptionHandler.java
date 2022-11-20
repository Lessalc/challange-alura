package com.lessalc.aluraflix.resources.exception;

import java.util.ArrayList;
import java.util.List;

import com.lessalc.aluraflix.services.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.lessalc.aluraflix.services.exception.ResourceNotFoundException;

@RestControllerAdvice
public class ResourceExceptionHandler {
	
	@Autowired
	MessageSource messageSource;
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> resourceNotFoundException(ResourceNotFoundException e){
		HttpStatus status = HttpStatus.NOT_FOUND;
		return ResponseEntity.status(status).body(new StandardError(e.getMessage()));
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<StandardError> BadRequestException(BadRequestException e){
		HttpStatus status = HttpStatus.BAD_REQUEST;
		return ResponseEntity.status(status).body(new StandardError(e.getMessage()));
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ValidationError> handleValidation(MethodArgumentNotValidException exception){
		
		List<ValidationError> validationErros = new ArrayList<>();
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		
		fieldErrors.forEach(e -> {
			String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
			ValidationError validation = new ValidationError(e.getField(), mensagem);
			validationErros.add(validation);
		});
		return validationErros;
	}

}
