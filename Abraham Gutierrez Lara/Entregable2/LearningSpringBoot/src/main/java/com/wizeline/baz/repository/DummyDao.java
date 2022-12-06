package com.wizeline.baz.repository;

import java.util.Optional;

import com.wizeline.baz.model.PostalCodeInfo;

public interface DummyDao {
	
	Optional<PostalCodeInfo> getPostalCodeInfo(String postalCode, String countryAbbreviation);
}
