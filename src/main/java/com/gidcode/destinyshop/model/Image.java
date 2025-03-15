package com.gidcode.destinyshop.model;

import java.sql.Blob;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

@Entity
public record Image(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id,
    String fileName,
    String fileType,
    @Lob
    Blob image,
    String downloadUrl,
    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product
) {
}