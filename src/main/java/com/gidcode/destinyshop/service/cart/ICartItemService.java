package com.gidcode.destinyshop.service.cart;

import com.gidcode.destinyshop.model.Cart;
import com.gidcode.destinyshop.model.CartItem;

import java.util.Optional;

public interface ICartItemService {
    void addItemToCart(Long cartId, Long productId, int quantity);
    void removeItemFromCart(Long cartId, Long productId);

    Optional<CartItem> getCartItem(Long productId, Cart cart);

    void updateItemQuantity(Long cartId, Long productId, int quantity);
}
