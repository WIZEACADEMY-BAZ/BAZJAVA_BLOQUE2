package com.wizeline.baz.exceptions;

import java.util.Map;

import com.wizeline.baz.model.OperationData;
import com.wizeline.baz.utils.BuildOperationData;

public class FailedLoginException extends RuntimeException implements BuildOperationData {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Map<String, Object> operationData;
	
	public FailedLoginException(Map<String, Object> operationData) {
		this.operationData = operationData;
	}
	
	@Override
	public OperationData operationData() {
		return new OperationData("FAILED_LOGIN",  operationData);
	}

}
