package com.gidcode.destinyshop.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
//    private BigDecimal totalAmount = BigDecimal.ZERO;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> cartItems = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void addItem(CartItem item) {
        this.cartItems.add(item);
        item.setCart(this);
//        updateTotalAmount();
    }

    public void removeItem(CartItem item) {
        this.cartItems.remove(item);
        item.setCart(null);
    }
        
    public BigDecimal getTotalAmount() {
        //            BigDecimal unitPrice = item.getProduct().getPrice();
        //            if (unitPrice == null) {
        //                return BigDecimal.ZERO;
        //            }
        //
        //            return unitPrice.multiply(BigDecimal.valueOf(item.getQuantity()));
        return cartItems.stream().map(CartItem::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
