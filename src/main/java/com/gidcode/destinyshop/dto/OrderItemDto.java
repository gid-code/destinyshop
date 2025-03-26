package com.gidcode.destinyshop.dto;

import java.math.BigDecimal;
import java.util.List;

public record OrderItemDto(
        Long productId,
        String productName,
        String productBrand,
        int quantity,
        BigDecimal price,
        List<ImageDto> images
) {
}
