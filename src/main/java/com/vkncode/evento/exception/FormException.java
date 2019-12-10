package com.vkncode.evento.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FormException {

	@Autowired
	private MessageSource messageSource;
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<FormError> handle(MethodArgumentNotValidException exception) {
		List<FormError> errorList = new ArrayList<FormError>();
		
		List<FieldError> formErrors = exception.getBindingResult().getFieldErrors();
		formErrors.forEach(e -> {
			String message = messageSource.getMessage(e, LocaleContextHolder.getLocale());
			errorList.add(new FormError(e.getObjectName(), e.getField(), message));
		});
		
		return errorList;
	}
	
	public class FormError {
		public String object;
		public String field;
		public String message;
		
		public FormError(String object, String field, String message) {
			this.object = object;
			this.field = field;
			this.message = message;
		}
		
		public String getObject() {
			return object;
		}
		
		public String getField() {
			return field;
		}
		
		public String getMessage() {
			return message;
		}
	}
}

