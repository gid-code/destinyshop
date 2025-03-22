package com.gidcode.destinyshop.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gidcode.destinyshop.dto.CartItemDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private int quantity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    @JsonIgnore
    private Cart cart;


    public BigDecimal getTotalPrice() {
        return this.getProduct().getPrice().multiply(new BigDecimal(quantity));
    }

    public BigDecimal getUnitPrice() {
        return this.getProduct().getPrice();
    }

    public CartItemDto toDto(){
        return new CartItemDto(
                id,
                product.toProductDto(),
                quantity,
                getTotalPrice()
        );
    }
}
