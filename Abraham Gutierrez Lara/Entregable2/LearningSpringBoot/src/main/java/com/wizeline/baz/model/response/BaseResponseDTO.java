package com.wizeline.baz.model.response;

import com.wizeline.baz.enums.ResponseStatus;
import com.wizeline.baz.model.ErrorDTO;
import com.wizeline.baz.utils.StatusCodes;

public class BaseResponseDTO {

	private ResponseStatus status;
	private String code;
	private ErrorDTO errors;
	
	protected BaseResponseDTO() {
		
	}
	
	private BaseResponseDTO(BaseResponseBuilder builder) {
		this.status = builder.status;
		this.code = builder.code;
		this.errors = builder.errors;
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
	
	public static BaseResponseBuilder builder() {
		return new BaseResponseBuilder();
	}
	
	public static final class BaseResponseBuilder {
		private ResponseStatus status;
		private String code;
		private ErrorDTO errors;
		
		private BaseResponseBuilder() {
		}
		
		public BaseResponseBuilder status(ResponseStatus status) {
			this.status = status;
			return this;
		}
		public BaseResponseBuilder code(String code) {
			this.code = code;
			return this;
		}
		public BaseResponseBuilder errors(ErrorDTO errors) {
			this.errors = errors;
			return this;
		}
		
		public BaseResponseDTO build() {
			return new BaseResponseDTO(this);
		}
	}
	
	
}
