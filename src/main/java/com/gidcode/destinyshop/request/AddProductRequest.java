package com.gidcode.destinyshop.request;


import com.gidcode.destinyshop.model.Category;
import lombok.Data;

import java.math.BigDecimal;

//@Data
//public class AddProductRequest{
//    private String name;
//    private String brand;
//    private BigDecimal price;
//    private int inventory;
//    private String description;
//    private Category category;
//}


public record AddProductRequest(
        String name,
        String brand,
        BigDecimal price,
        int inventory,
        String description,
        Category category
){

}
