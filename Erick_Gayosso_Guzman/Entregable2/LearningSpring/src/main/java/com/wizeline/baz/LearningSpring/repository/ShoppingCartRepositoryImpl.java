package com.wizeline.baz.LearningSpring.repository;


import com.wizeline.baz.LearningSpring.model.CartDTO;
import com.wizeline.baz.LearningSpring.model.ProductsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Component
public class ShoppingCartRepositoryImpl {

    private static final Logger LOGGER = Logger.getLogger(ShoppingCartRepositoryImpl.class.getName());

    @Autowired
    MongoTemplate mongoTemplate;

    public List<ProductsDTO> listProducts() {
        return mongoTemplate.findAll(ProductsDTO.class);
    }

    public void addProductToCar(ProductsDTO productsDTO) {
        mongoTemplate.save(productsDTO);
    }

    public void deleteProductToCar(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").lte(name)).limit(1);
        mongoTemplate.findAndRemove(query, ProductsDTO.class);
    }

    public void updateProductToCar(ProductsDTO cart) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").lte(cart.getName())).limit(1);
        Update update = new Update().set("name", cart.getName()).set("price", cart.getPrice()).set("description", cart.getDescription()).set("amount", cart.getAmount());
        mongoTemplate.findAndModify(query, update, ProductsDTO.class);
    }

    public void savePurchase(CartDTO cartDTO){
        mongoTemplate.save(cartDTO);
    }

    public void deleteAll(ProductsDTO productsDTO){
        mongoTemplate.remove(productsDTO);
    }

    public List<CartDTO> getMyPurchases(){
        return mongoTemplate.findAll(CartDTO.class);
    }
}
