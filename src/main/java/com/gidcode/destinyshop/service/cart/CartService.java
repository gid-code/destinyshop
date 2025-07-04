package com.gidcode.destinyshop.service.cart;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import com.gidcode.destinyshop.model.User;
import com.gidcode.destinyshop.repository.CartItemRepository;
import org.springframework.stereotype.Service;

import com.gidcode.destinyshop.exception.CartNotFoundException;
import com.gidcode.destinyshop.model.Cart;
import com.gidcode.destinyshop.repository.CartRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService{
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public Cart getCart(Long id) {
        Cart cart = cartRepository.findById(id)
        .orElseThrow(()-> new CartNotFoundException("Cart with id "+id+" not found"));

        return cartRepository.save(cart);
    }

    @Override
    public void clearCart(Long id) {
        Cart cart = getCart(id);
        cart.getCartItems().clear();
        cartRepository.deleteById(id);
    }

    @Override
    public BigDecimal getTotalAmount(Long id) {
       Cart cart = getCart(id);
       return cart.getTotalAmount();
    }

    @Override
    public Cart initializeCart(User user){
        return Optional.ofNullable(getCartByUserId(user.getId()))
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUser(user);
                    return cartRepository.save(cart);
                });
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

}
