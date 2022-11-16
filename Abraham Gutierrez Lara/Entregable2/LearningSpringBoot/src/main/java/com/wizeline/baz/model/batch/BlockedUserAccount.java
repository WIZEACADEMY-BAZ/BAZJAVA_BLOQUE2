package com.wizeline.baz.model.batch;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class BlockedUserAccount {
	
	private String userId;
	private String email;
	private LocalDateTime time;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public LocalDateTime getTime() {
		return time;
	}
	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	public Map<String, Object> toMap() {
		Map<String, Object> operationData = new HashMap<>();
		operationData.put("userId", this.userId);
		operationData.put("email", this.email);
		operationData.put("time", this.time.toString());
		return operationData;
	}
}
