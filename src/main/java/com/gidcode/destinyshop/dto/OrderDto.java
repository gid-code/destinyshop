package com.gidcode.destinyshop.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record OrderDto(
        Long id,
        Long userId,
        LocalDate orderDate,
        BigDecimal totalAmount,
        String status,
        List<OrderItemDto> orderItems
) {
}
