package com.gidcode.destinyshop.service.order;

import com.gidcode.destinyshop.dto.OrderDto;
import com.gidcode.destinyshop.enums.OrderStatus;
import com.gidcode.destinyshop.exception.OrderNotFoundException;
import com.gidcode.destinyshop.model.Cart;
import com.gidcode.destinyshop.model.Order;
import com.gidcode.destinyshop.model.OrderItem;
import com.gidcode.destinyshop.model.Product;
import com.gidcode.destinyshop.repository.OrderRepository;
import com.gidcode.destinyshop.repository.ProductRepository;
import com.gidcode.destinyshop.service.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ICartService cartService;

    @Override
    public OrderDto placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);

        Order order = createOrder(cart);
        List<OrderItem> orderItems = createOrderItems(order,cart);

        order.setOrderItems(new HashSet<>(orderItems));
        order.setTotalAmount(cart.getTotalAmount());
        Order savedOrder = orderRepository.save(order);

        cartService.clearCart(cart.getId());
        return savedOrder.toDto();
    }

    private Order createOrder(Cart cart){
        Order order = new Order();
        order.setStatus(OrderStatus.PENDING);
        order.setDate(LocalDate.now());
        order.setUser(cart.getUser());
        return order;
    }

    private List<OrderItem> createOrderItems(Order order, Cart cart){
        return cart.getCartItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            product.setInventory(product.getInventory() - cartItem.getQuantity());
            productRepository.save(product);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setUnitPrice(cartItem.getUnitPrice());
            return orderItem;
        }).toList();
    }

    @Override
    public OrderDto getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .map(this::convertToDto)
                .orElseThrow(()-> new OrderNotFoundException("Order of id "+orderId+" not found"));
    }

    @Override
    public List<OrderDto> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId)
                .stream().map(this::convertToDto).toList();
    }

    private OrderDto convertToDto(Order order){
        return order.toDto();
    }
}
