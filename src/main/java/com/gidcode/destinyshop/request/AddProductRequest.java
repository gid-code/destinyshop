package com.gidcode.destinyshop.request;


import com.gidcode.destinyshop.model.Category;

import java.math.BigDecimal;

public record AddProductRequest(
    String name,
    String brand,
    BigDecimal price,
    int inventory,
    String description,
    Category category
) {
}
