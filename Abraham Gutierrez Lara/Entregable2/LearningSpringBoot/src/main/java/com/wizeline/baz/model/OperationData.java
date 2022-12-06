package com.wizeline.baz.model;

import java.util.Map;
import java.util.UUID;

public class OperationData {
	private final String id;
	private final String flowName;
	private final Map<String, Object> data;
	
	public OperationData(String flowName, Map<String, Object> data) {
		this.id = UUID.randomUUID().toString();
		this.flowName = flowName;
		this.data = data;
	}
	
	
	public String getId() {
		return id;
	}
	public String getFlowName() {
		return flowName;
	}
	public Map<String, Object> getData() {
		return data;
	}
}
