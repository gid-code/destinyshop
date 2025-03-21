package com.gidcode.destinyshop.model;

import com.gidcode.destinyshop.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    private int quantity;
    private BigDecimal unitPrice;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public OrderItem(Order order, int quantity, BigDecimal unitPrice, Product product) {
        this.order = order;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.product = product;
    }
}
