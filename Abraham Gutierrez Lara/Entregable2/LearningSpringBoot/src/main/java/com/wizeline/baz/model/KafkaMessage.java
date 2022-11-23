package com.wizeline.baz.model;

import java.util.Map;

public class KafkaMessage {
	private String id;
	private String flowName;
	private Map<String, Object> data;
	
	public KafkaMessage() {
		
	}
	
	public KafkaMessage(String id, String flowName, Map<String, Object> data) {
		this.flowName = flowName;
		this.data = data;
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFlowName() {
		return flowName;
	}
	public void setFlowName(String flow) {
		this.flowName = flow;
	}
	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
}
