package com.gidcode.destinyshop.service.order;

import com.gidcode.destinyshop.dto.OrderDto;

import java.util.List;

public interface IOrderService {
    OrderDto placeOrder(Long userId);
    OrderDto getOrder(Long orderId);
    List<OrderDto> getUserOrders(Long userId);
}
