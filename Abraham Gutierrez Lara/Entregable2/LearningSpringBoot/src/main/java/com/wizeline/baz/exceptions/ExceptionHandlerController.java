package com.wizeline.baz.exceptions;

import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.wizeline.baz.enums.ResponseStatus;
import com.wizeline.baz.model.ErrorDTO;
import com.wizeline.baz.model.response.BaseResponseDTO;
import com.wizeline.baz.utils.StatusCodes;

@RestControllerAdvice
public class ExceptionHandlerController {
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<BaseResponseDTO> userNotFoundException(UserNotFoundException ex) {
		ErrorDTO error = new ErrorDTO(StatusCodes.USER_DOESNT_EXIST, "User -> " +  ex.getUser());
		return new ResponseEntity<>(new BaseResponseDTO(ResponseStatus.FAILED, StatusCodes.FAILED, error),
									HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<BaseResponseDTO> constraintViolationException(MethodArgumentNotValidException ex) {
		String errors = ex.getAllErrors().stream().map(error -> error.getDefaultMessage()).
							collect(Collectors.joining(System.lineSeparator()));
		ErrorDTO error = new ErrorDTO(StatusCodes.INVALID_REQUES_INPUT, "Errors -> " + errors);
		return new ResponseEntity<>(new BaseResponseDTO(ResponseStatus.FAILED, StatusCodes.FAILED, error),
				HttpStatus.NOT_FOUND);
	}
}
