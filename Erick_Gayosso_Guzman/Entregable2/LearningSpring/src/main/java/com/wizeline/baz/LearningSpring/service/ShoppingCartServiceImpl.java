package com.wizeline.baz.LearningSpring.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wizeline.baz.LearningSpring.exceptions.MethodArgumentNotValid;
import com.wizeline.baz.LearningSpring.model.CartDTO;
import com.wizeline.baz.LearningSpring.model.ProductsDTO;
import com.wizeline.baz.LearningSpring.model.ResponseDTO;
import com.wizeline.baz.LearningSpring.repository.ShoppingCartRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.wizeline.baz.LearningSpring.utils.Utils;

import java.time.LocalDate;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private static final Logger LOGGER = Logger.getLogger(ShoppingCartServiceImpl.class.getName());

    @Autowired
    ShoppingCartRepositoryImpl cartRepository;

    @Override
    public ResponseEntity<String> getListProducts() throws JsonProcessingException {
        LOGGER.info("Obteniendo lista de productos ... ");
        List<ProductsDTO> getList = cartRepository.listProducts();
        ObjectMapper theBadMapper = new ObjectMapper();
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode("OK000");
        responseDTO.setStatus("success");
        responseDTO.setResults(getList);
        String theJsonText = theBadMapper.writeValueAsString(responseDTO);
        return new ResponseEntity<>(theJsonText, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> addToCar(ProductsDTO productsDTO) throws MethodArgumentNotValid {
        LOGGER.info("Agegando producto a carrito ... ");
        if (Utils.isDecimal(String.valueOf(productsDTO.getPrice()))) {
            cartRepository.addProductToCar(productsDTO);
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setCode("OK000");
            responseDTO.setStatus("success");
            return new ResponseEntity<>(responseDTO.toString(), HttpStatus.OK);
        } else {
            throw new MethodArgumentNotValid("FAIL000", "Bad Request");
        }

    }

    @Override
    public ResponseEntity<String> deleteToCar(String id) {
        LOGGER.info("Eliminando producto de carrito ... ");
        cartRepository.deleteProductToCar(id);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode("OK000");
        responseDTO.setStatus("success");
        return new ResponseEntity<>(responseDTO.toString(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> updateToCar(ProductsDTO cartDTO) {
        LOGGER.info("Actualizando producto de carrito ... ");
        cartRepository.updateProductToCar(cartDTO);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode("OK000");
        responseDTO.setStatus("success");
        return new ResponseEntity<>(responseDTO.toString(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> generatePurchase() {

        long init = System.currentTimeMillis();
        LOGGER.info("Iniciando compra ...");
        CartDTO dto = new CartDTO();
        List<ProductsDTO> productsList = cartRepository.listProducts();
        Optional<List<ProductsDTO>> optional = Optional.of(productsList);

        if (optional.isPresent()){
            dto.setProductsDTO(productsList);
            dto.setTotalArticulos(String.valueOf(productsList.stream().count()));
            long total = productsList.stream().mapToLong(ProductsDTO::getPrice).sum();
            dto.setTotal(total);
            dto.setDate(LocalDate.now());
            cartRepository.savePurchase(dto);
            LOGGER.info("Procesando compra, espere un momento...");
            this.waitSeconds(5);
            productsList.stream().forEach(p -> {
                cartRepository.deleteAll(p);
            });
            LOGGER.info("Finalizando compra en: "+ (System.currentTimeMillis()-init) + " ms" );
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setCode("OK000");
            responseDTO.setStatus("success");
            return new ResponseEntity<>(responseDTO.toString(), HttpStatus.OK);
        }else{
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setCode("FAIL000");
            responseDTO.setStatus("FAIL");
            return new ResponseEntity<>(responseDTO.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<String> getMyPurchasesBefore() throws JsonProcessingException {
        List<CartDTO> list = cartRepository.getMyPurchases();
        ObjectMapper theBadMapper = new ObjectMapper();
        list =  list.stream().filter(p -> p.getDate().isBefore(LocalDate.now())).collect(Collectors.toList());
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode("OK000");
        responseDTO.setStatus("success");
        responseDTO.setResults(list);
        String theJsonText = theBadMapper.writeValueAsString(responseDTO);
        return new ResponseEntity<>(theJsonText, HttpStatus.OK);
    }


    private void waitSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

}
