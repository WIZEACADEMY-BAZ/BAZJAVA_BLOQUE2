package com.wizeline.baz.model.response;

public class LoginResponse extends BaseResponseDTO {
	private String jwtToken;

	public String getJwtToken() {
		return jwtToken;
	}
	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}	
}
