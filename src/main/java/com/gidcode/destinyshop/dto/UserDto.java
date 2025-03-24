package com.gidcode.destinyshop.dto;

import java.util.List;

public record UserDto(
        Long id,
        String firstName,
        String lastName,
        String email,
        List<OrderDto> orders,
        CartDto cart
) {
}
