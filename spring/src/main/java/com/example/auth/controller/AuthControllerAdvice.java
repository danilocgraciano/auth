package com.example.auth.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.auth.entity.ApiError;

@RestControllerAdvice
public class AuthControllerAdvice {

	@Autowired
	private MessageSource messageSource;

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ApiError> handle(MethodArgumentNotValidException exception) {
		List<ApiError> dto = new ArrayList<>();

		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		fieldErrors.forEach(e -> {
			String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
			ApiError erro = new ApiError(e.getField(), mensagem);
			dto.add(erro);
		});

		return dto;
	}

	@ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ApiError handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex) {

		StringBuilder builder = new StringBuilder();
		builder.append(ex.getMethod());
		builder.append(" method is not supported for this request. Supported methods are ");
		ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

		return new ApiError(builder.toString());
	}

	@ResponseStatus(code = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ApiError handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex) {

		StringBuilder builder = new StringBuilder();
		builder.append(ex.getContentType());
		builder.append(" media type is not supported. Supported media types are ");
		ex.getSupportedMediaTypes().forEach(t -> builder.append(t + ", "));

		return new ApiError(builder.toString());
	}

	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ApiError handleEmptyResultDataAccessException(EmptyResultDataAccessException ex) {

		return new ApiError(ex.getMessage());
	}

	@ResponseStatus(code = HttpStatus.CONFLICT)
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ApiError handleDataIntegrityViolationException(DataIntegrityViolationException ex) {

		return new ApiError(ex.getMessage());
	}

//	@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
//	@ExceptionHandler(InvalidOperationException.class)
//	public ApiError handleInvalidOperationException(InvalidOperationException ex) {
//
//		return new ApiError(ex.getMessage());
//	}

	@ResponseStatus(code = HttpStatus.PRECONDITION_FAILED)
	@ExceptionHandler(ObjectOptimisticLockingFailureException.class)
	public ApiError handleObjectOptimisticLockingFailureException(ObjectOptimisticLockingFailureException ex) {

		return new ApiError("Optimistic locking failed - Row was updated or deleted by another transaction");
	}

	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ApiError handleException(Exception ex) {
		ex.printStackTrace();
		return new ApiError("error occurred");
	}

}
