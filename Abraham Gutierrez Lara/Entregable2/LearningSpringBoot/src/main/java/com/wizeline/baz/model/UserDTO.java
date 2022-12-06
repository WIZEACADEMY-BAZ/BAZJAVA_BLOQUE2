package com.wizeline.baz.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.wizeline.baz.enums.UserRole;

@Document("Users")
public class UserDTO {
	@Id
	private String id;
	private String name;
	private String password;
	private String email;
	private List<UserRole> roles;
	private LocalDateTime creationTime;
	private LocalDateTime updateTime;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public LocalDateTime getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(LocalDateTime creationTime) {
		this.creationTime = creationTime;
	}
	public LocalDateTime getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}
	public List<UserRole> getRoles() {
		return roles;
	}
	public void setRoles(List<UserRole> roles) {
		this.roles = roles;
	}
	
}
