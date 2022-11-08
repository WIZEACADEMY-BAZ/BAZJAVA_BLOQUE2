package com.wizeline.baz.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.wizeline.baz.enums.ResponseStatus;
import com.wizeline.baz.model.ErrorDTO;
import com.wizeline.baz.model.PostalCodeInfo;
import com.wizeline.baz.model.response.BaseResponseDTO;
import com.wizeline.baz.model.response.PostalCodeResponse;
import com.wizeline.baz.repository.DummyDao;
import com.wizeline.baz.utils.StatusCodes;

@Service
public class DummyServiceImpl implements DummyService {
	
	@Autowired
	private DummyDao dummyDao;

	@Override
	public ResponseEntity<BaseResponseDTO> getPostalCodeInfo(String postalCode) {
		return getPostalCodeInfo(postalCode, "mx");
	}

	@Override
	public ResponseEntity<BaseResponseDTO> getPostalCodeInfo(String postalCode, String countryAbbreviation) {
		Optional<PostalCodeInfo> postalCodeInfoOpt = dummyDao.getPostalCodeInfo(postalCode, countryAbbreviation);
		if(!postalCodeInfoOpt.isPresent()) {
			ErrorDTO error = new ErrorDTO(StatusCodes.POSTAL_CODE_DOESNT_EXIST, "PostalCode -> " + postalCode);
			BaseResponseDTO response = new BaseResponseDTO(ResponseStatus.FAILED, StatusCodes.FAILED, error);
			return new ResponseEntity<BaseResponseDTO>(response, HttpStatus.NOT_FOUND);
		}
		PostalCodeResponse response = new PostalCodeResponse();
		response.setPostalCodeInfo(postalCodeInfoOpt.get());
		response.makeSuccess();
		return ResponseEntity.ok(response);
	}

}
