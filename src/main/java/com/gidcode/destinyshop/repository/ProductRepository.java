package com.gidcode.destinyshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gidcode.destinyshop.model.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>{

    List<Product> findByCategoryName(String category);

    List<Product> findByBrand(String band);

    List<Product> findByCategoryNameAndBrand(String category, String brand);

    List<Product> findByName(String product);

    List<Product> findByBrandAndName(String brand, String product);

    long countByBrandAndName(String brand, String product);
}
