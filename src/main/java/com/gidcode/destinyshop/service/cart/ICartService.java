package com.gidcode.destinyshop.service.cart;

import java.math.BigDecimal;

import com.gidcode.destinyshop.model.Cart;
import com.gidcode.destinyshop.model.User;

public interface ICartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalAmount(Long id);
    Cart initializeCart(User user);
    Cart getCartByUserId(Long userId);
}
