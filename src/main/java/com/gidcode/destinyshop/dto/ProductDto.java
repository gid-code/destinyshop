package com.gidcode.destinyshop.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale.Category;

public record ProductDto(
    String name,
    String brand,
    BigDecimal price,
    int inventory,
    String description,
    Category category,
    List<ImageDto> images
) {
    
}
