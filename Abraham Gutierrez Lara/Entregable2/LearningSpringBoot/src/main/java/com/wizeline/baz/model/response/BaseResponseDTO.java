package com.wizeline.baz.model.response;

import com.wizeline.baz.enums.ResponseStatus;
import com.wizeline.baz.model.ErrorDTO;
import com.wizeline.baz.utils.StatusCodes;

public class BaseResponseDTO {

	private ResponseStatus status;
	private String code;
	private ErrorDTO errors;
	
	public BaseResponseDTO() {
	}
	
	public BaseResponseDTO(ResponseStatus status, String code, ErrorDTO errors) {
		this.status = status;
		this.code = code;
		this.errors = errors;
	}
	
	public ResponseStatus getStatus() {
		return status;
	}
	public void setStatus(ResponseStatus status) {
		this.status = status;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public ErrorDTO getErrors() {
		return errors;
	}
	public void setErrors(ErrorDTO errors) {
		this.errors = errors;
	}
	
	public void makeSuccess() {
		this.code = StatusCodes.SUCESS;
		this.status = ResponseStatus.OK;
	}
}
