package com.gidcode.destinyshop.service.order;

import com.gidcode.destinyshop.dto.OrderDto;
import com.gidcode.destinyshop.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);
    List<OrderDto> getUserOrders(Long userId);
}
