package com.gidcode.destinyshop.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
public record Category(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id,
    String name,
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Product> products
) {
    
}
