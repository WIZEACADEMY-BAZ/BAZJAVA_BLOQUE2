package com.wizeline.baz.LearningSpring.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wizeline.baz.LearningSpring.exceptions.MethodArgumentNotValid;
import com.wizeline.baz.LearningSpring.model.CartDTO;
import com.wizeline.baz.LearningSpring.model.ProductsDTO;
import org.springframework.http.ResponseEntity;


public interface ShoppingCartService {

    public ResponseEntity<String> getListProducts() throws JsonProcessingException;
    public ResponseEntity<String> addToCar(ProductsDTO productsDTO) throws MethodArgumentNotValid;
    public ResponseEntity<String> deleteToCar(String id);
    public ResponseEntity<String> updateToCar(ProductsDTO productsDTO);

    public ResponseEntity<String> generatePurchase();

    public ResponseEntity<String> getMyPurchasesBefore() throws JsonProcessingException;


}
