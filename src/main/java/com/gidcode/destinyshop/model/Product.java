package com.gidcode.destinyshop.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public record Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id,
    String name,
    String brand,
    BigDecimal price,
    int inventory,
    String description,
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    Category category,
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Image> images
) {

    public Product(String name, String brand, BigDecimal price, int inventory, String description, Category category) {
        this(0, name, brand, price, inventory, description, category, List.of());
    }
}
