package com.gidcode.destinyshop.dto;

import com.gidcode.destinyshop.model.Category;

import java.math.BigDecimal;
import java.util.List;

public record ProductDto(
    String name,
    String brand,
    BigDecimal price,
    int inventory,
    String description,
    Category category,
    List<ImageDto> images
) {
//    public ProductDto(String name, String brand, BigDecimal price, int inventory, String description, Category category) {
//        this(name, brand, price, inventory, description, category, null);
//    }
}
