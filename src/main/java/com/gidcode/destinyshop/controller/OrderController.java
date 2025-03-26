package com.gidcode.destinyshop.controller;


import com.gidcode.destinyshop.dto.OrderDto;
import com.gidcode.destinyshop.exception.OrderNotFoundException;
import com.gidcode.destinyshop.response.ApiResponse;
import com.gidcode.destinyshop.service.order.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/order")
public class OrderController extends BaseController{
    private final IOrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId) {
        try {
            OrderDto order = orderService.placeOrder(userId);
            return ResponseEntity.ok(new ApiResponse("Item Order Success!", order));
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId) {
        try {
            OrderDto order = orderService.getOrder(orderId);
            return ResponseEntity.ok(new ApiResponse("Item Order Success!", order));
        } catch (OrderNotFoundException e) {
            return handleException(e);
        }
    }

    @GetMapping("/{userId}/user")
    public ResponseEntity<ApiResponse> getUserOrders(@PathVariable Long userId) {
        try {
            List<OrderDto> orders = orderService.getUserOrders(userId);
            return ResponseEntity.ok(new ApiResponse("Item Order Success!", orders));
        } catch (Exception e) {
            return handleException(e);
        }
    }
}
