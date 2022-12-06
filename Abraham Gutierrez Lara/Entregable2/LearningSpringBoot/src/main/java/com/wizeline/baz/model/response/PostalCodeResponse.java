package com.wizeline.baz.model.response;

import com.wizeline.baz.model.PostalCodeInfo;

public class PostalCodeResponse extends BaseResponseDTO {
	
	private PostalCodeInfo postalCodeInfo;

	public PostalCodeInfo getPostalCodeInfo() {
		return postalCodeInfo;
	}
	public void setPostalCodeInfo(PostalCodeInfo postalCodeInfo) {
		this.postalCodeInfo = postalCodeInfo;
	}
}
