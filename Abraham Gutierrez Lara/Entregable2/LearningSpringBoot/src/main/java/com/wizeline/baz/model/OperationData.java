package com.wizeline.baz.model;

import java.util.Map;

public class OperationData {
	private final String flowName;
	private final Map<String, Object> operationData;
	
	public OperationData(String flowName, Map<String, Object> operationData) {
		super();
		this.flowName = flowName;
		this.operationData = operationData;
	}
	
	public String getFlowName() {
		return flowName;
	}
	public Map<String, Object> getOperationData() {
		return operationData;
	}
}
