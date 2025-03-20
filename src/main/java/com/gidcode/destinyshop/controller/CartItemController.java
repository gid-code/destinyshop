package com.gidcode.destinyshop.controller;

import com.gidcode.destinyshop.exception.CartNotFoundException;
import com.gidcode.destinyshop.response.ApiResponse;
import com.gidcode.destinyshop.service.cart.ICartItemService;
import com.gidcode.destinyshop.service.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cart-items")
public class CartItemController {
    private final ICartItemService cartItemService;
    private final ICartService cartService;

    @PostMapping
    public ResponseEntity<ApiResponse> addItemToCart(
            @RequestParam(required = false) Long cartId,
            @RequestParam Long productId,
            @RequestParam int quantity
    ) {
        try {
            if (cartId == null){
                cartId = cartService.initializeCart();
            }
            cartItemService.addItemToCart(cartId,productId,quantity);
            return ResponseEntity.ok(new ApiResponse("Item added to cart", null));
        } catch (CartNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse> removeItemFromCart(
            @RequestParam Long cartId,
            @RequestParam Long productId
    ) {
        try {
            cartItemService.removeItemFromCart(cartId,productId);
            return ResponseEntity.ok(new ApiResponse("Item removed to cart", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping
    public ResponseEntity<ApiResponse> updateItemQuantity(
            @RequestParam Long cartId,
            @RequestParam Long productId,
            @RequestParam int quantity
    ) {
        try {
            cartItemService.updateItemQuantity(cartId,productId,quantity);
            return ResponseEntity.ok(new ApiResponse("Update Item success", null));
        } catch (CartNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
