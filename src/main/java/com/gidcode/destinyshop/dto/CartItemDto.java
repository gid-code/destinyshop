package com.gidcode.destinyshop.dto;

import java.math.BigDecimal;

public record CartItemDto(
        Long id,
        ProductDto product,
        int quantity,
        BigDecimal totalPrice
) {
}
