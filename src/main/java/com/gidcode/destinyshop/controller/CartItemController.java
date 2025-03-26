package com.gidcode.destinyshop.controller;

import com.gidcode.destinyshop.exception.CartNotFoundException;
import com.gidcode.destinyshop.exception.ProductNotFoundException;
import com.gidcode.destinyshop.model.User;
import com.gidcode.destinyshop.response.ApiResponse;
import com.gidcode.destinyshop.service.cart.ICartItemService;
import com.gidcode.destinyshop.service.cart.ICartService;
import com.gidcode.destinyshop.service.user.IUserService;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cart-item")
public class CartItemController extends BaseController{
    private final ICartItemService cartItemService;
    private final ICartService cartService;
    private final IUserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse> addItemToCart(
            @RequestParam Long productId,
            @RequestParam int quantity
    ) {
        try {
            User user = userService.getAuthenticatedUser();
            Long cartId = cartService.initializeCart(user).getId();

            cartItemService.addItemToCart(cartId,productId,quantity);
            return ResponseEntity.ok(new ApiResponse("Item added to cart", null));
        } catch (CartNotFoundException | JwtException e) {
            return handleException(e);
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
        } catch ( Exception e) {
            return handleException(e);
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
            return handleException(e);
        }
    }
}
