package com.wizeline.baz.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wizeline.baz.model.PostalCodeInfo;

@ExtendWith(MockitoExtension.class)
public class DummyDaoImplTest {
	
	@InjectMocks
	private DummyDaoImpl dummyDao;
	
	@Test
	void postalCodeInfo() {
		Optional<PostalCodeInfo> optPostalCodeInfo = dummyDao.getPostalCodeInfo("04660", "mx");
		assertTrue(optPostalCodeInfo.isPresent());
		PostalCodeInfo postalCodeInfo = optPostalCodeInfo.get();
		assertNotNull(postalCodeInfo);
	}
	
	@Test
	void wrongPostalCodeInfo() {
		Optional<PostalCodeInfo> optPostalCodeInfo = dummyDao.getPostalCodeInfo("", "mx");
		assertFalse(optPostalCodeInfo.isPresent());
	}
}
