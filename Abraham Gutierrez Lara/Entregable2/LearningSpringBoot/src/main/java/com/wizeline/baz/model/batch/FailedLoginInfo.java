package com.wizeline.baz.model.batch;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class FailedLoginInfo {
	private String userId;
	private String email;
	private String failedPassword;
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
	public String getFailedPassword() {
		return failedPassword;
	}
	public void setFailedPassword(String failedPassword) {
		this.failedPassword = failedPassword;
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
		operationData.put("failedPassword", this.failedPassword);
		operationData.put("time", this.time.toString());
		return operationData;
	}
	
	public static FailedLoginInfo fromMap(Map<String, Object> mapInfo) {
		FailedLoginInfo info = new FailedLoginInfo();
		info.setEmail((String) mapInfo.get("email"));
		info.setFailedPassword((String) mapInfo.get("failedPassword"));
		info.setUserId((String) mapInfo.get("userId"));
		info.setTime(LocalDateTime.parse((String)mapInfo.get("time")));
		return info;
	}
	
}
