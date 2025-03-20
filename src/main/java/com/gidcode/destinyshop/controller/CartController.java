package com.gidcode.destinyshop.controller;

import com.gidcode.destinyshop.exception.CartNotFoundException;
import com.gidcode.destinyshop.model.Cart;
import com.gidcode.destinyshop.response.ApiResponse;
import com.gidcode.destinyshop.service.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/carts")
public class CartController {
    private final ICartService cartService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCart(@PathVariable Long id) {
        try {
            Cart cart = cartService.getCart(id);
            return ResponseEntity.ok(new ApiResponse("Success",cart));
        } catch (CartNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable Long id) {
        try {
            cartService.clearCart(id);
            return ResponseEntity.ok(new ApiResponse("Clear Cart Success!", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{id}/total-price")
    public ResponseEntity<ApiResponse> getTotalAmount(@PathVariable Long id) {
        try {
            BigDecimal totalPrice = cartService.getTotalAmount(id);
            return ResponseEntity.ok(new ApiResponse("Total Price", totalPrice));
        } catch (CartNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
}
