package com.gidcode.destinyshop.service.order;

import com.gidcode.destinyshop.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    Order getOrder(Long orderId);
    List<Order> getUserOrders(Long userId);
}
