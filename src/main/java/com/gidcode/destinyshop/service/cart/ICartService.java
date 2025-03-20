package com.gidcode.destinyshop.service.cart;

import java.math.BigDecimal;

import com.gidcode.destinyshop.model.Cart;

public interface ICartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalAmount(Long id);

    Long initializeCart();
}
