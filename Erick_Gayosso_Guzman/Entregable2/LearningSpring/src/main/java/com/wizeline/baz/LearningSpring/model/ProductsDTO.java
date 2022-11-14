package com.wizeline.baz.LearningSpring.model;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Document(value = "Products")
public class ProductsDTO {

    private String name;
    private long price;
    private String description;

    private String amount;

    public ProductsDTO() {
    }

    public ProductsDTO(String name, long price, String description, String amount) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
