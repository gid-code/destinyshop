package com.gidcode.destinyshop.service.cart;

import com.gidcode.destinyshop.exception.ProductNotFoundException;
import com.gidcode.destinyshop.model.Cart;
import com.gidcode.destinyshop.model.CartItem;
import com.gidcode.destinyshop.model.Product;
import com.gidcode.destinyshop.repository.CartItemRepository;
import com.gidcode.destinyshop.repository.CartRepository;
import com.gidcode.destinyshop.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService{
    private final CartItemRepository cartItemRepository;
    private final IProductService productService;
    private final CartService cartService;
    private final CartRepository cartRepository;

    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        CartItem cartItem = getCartItem(productId, cart)
                .orElse(new CartItem());

        if (cartItem.getId() == null ){
            Product product = productService.getProductById(productId);
            cartItem.setCart(cart);
            cartItem.setQuantity(quantity);
            cartItem.setProduct(product);
//            cartItem.setUnitPrice(product.getPrice());
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }

//        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        CartItem cartItem = getCartItem(productId, cart)
                .orElseThrow(()-> new ProductNotFoundException("Product not found"));

        cart.removeItem(cartItem);
        cartItemRepository.delete(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        getCartItem(productId, cart)
                .ifPresent(item -> {
                    item.setQuantity(quantity);
//                    item.setTotalPrice();
                    cartItemRepository.save(item);
                });

//        cart.updateTotalAmount();
        cartRepository.save(cart);
    }

    @Override
    public Optional<CartItem> getCartItem(Long productId, Cart cart) {
        return cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();
    }
}
