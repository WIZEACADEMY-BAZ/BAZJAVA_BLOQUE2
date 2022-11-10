package com.wizeline.baz.LearningSpring.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wizeline.baz.LearningSpring.client.ApiPublicRestTemplate;
import com.wizeline.baz.LearningSpring.exceptions.MethodArgumentNotValid;
import com.wizeline.baz.LearningSpring.model.CartDTO;
import com.wizeline.baz.LearningSpring.model.ProductsDTO;
import com.wizeline.baz.LearningSpring.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

@RequestMapping("/api/")
@RestController
public class ShoppingCartController {

    private static final Logger LOGGER = Logger.getLogger(ShoppingCartController.class.getName());

    @Autowired
    ShoppingCartService service;

    @Autowired
    ApiPublicRestTemplate publicRestTemplate;

    @PreAuthorize("hasRole('GUEST')")
    @GetMapping("/getListProducts")
    public ResponseEntity<String> getMyListProducts() throws JsonProcessingException {
        LOGGER.info("Procesando peticion de tipo GET ... ");
        return service.getListProducts();
    }
    @PreAuthorize("hasRole('GUEST')")
    @PostMapping("/updateProducts")
    public ResponseEntity<String> updateMyProducts( @RequestBody ProductsDTO cartDTO){
        LOGGER.info("Procesando peticion de tipo POST ... ");
        return service.updateToCar(cartDTO);
    }
    @PreAuthorize("hasRole('GUEST')")

    @DeleteMapping("/deleteProducts/{id}")
    public ResponseEntity<String> deleteMyProducts(@PathVariable String id){
        LOGGER.info("Procesando peticion de tipo DELETE ... ");
        return service.deleteToCar(id);
    }
    @PreAuthorize("hasRole('GUEST')")
    @PutMapping("/addToCar")
    public ResponseEntity<String> addToCar(@RequestBody ProductsDTO productsDTO) throws MethodArgumentNotValid {
        LOGGER.info("Procesando peticion de tipo PUT ... ");
        return service.addToCar(productsDTO);
    }

    @Scheduled(fixedRate = 500000)
    @GetMapping("/getApiPublic")
    public ResponseEntity<String> getApiPublic() throws URISyntaxException, JsonProcessingException {
        LOGGER.info("Procesando peticion de tipo GET API PUBLIC ... ");

        ObjectMapper theBadMapper = new ObjectMapper();
        String theJsonText = theBadMapper.writeValueAsString(publicRestTemplate.apiPublic().getBody());
        return new ResponseEntity<>(theJsonText, HttpStatus.OK);
    }

    @GetMapping("/generatePurchase")
    public ResponseEntity<String> generatePurchase() {

        return service.generatePurchase();
    }

    @GetMapping("/listPurchases")
    public ResponseEntity<String> listPurchases() throws JsonProcessingException {

        return service.getMyPurchasesBefore();
    }


}
