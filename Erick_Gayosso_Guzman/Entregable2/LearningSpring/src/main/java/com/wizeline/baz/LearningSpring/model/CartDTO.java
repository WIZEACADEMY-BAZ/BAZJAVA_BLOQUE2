package com.wizeline.baz.LearningSpring.model;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDate;
import java.util.List;

@Document(value = "ShoppingCart")
public class CartDTO {

    //@Id
    //private String id;
    private List<ProductsDTO> productsDTO;
    private String totalArticulos;
    private Long total;
    private LocalDate date;

    public List<ProductsDTO> getProductsDTO() {
        return productsDTO;
    }

    public void setProductsDTO(List<ProductsDTO> productsDTO) {
        this.productsDTO = productsDTO;
    }

    public String getTotalArticulos() {
        return totalArticulos;
    }

    public void setTotalArticulos(String totalArticulos) {
        this.totalArticulos = totalArticulos;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


}
