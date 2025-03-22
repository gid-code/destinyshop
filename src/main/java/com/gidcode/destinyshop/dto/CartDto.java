package com.gidcode.destinyshop.dto;

import java.math.BigDecimal;
import java.util.Set;

public record CartDto(
        Long id,
        Set<CartItemDto> cartItems,
        BigDecimal totalAmount
) {
}
