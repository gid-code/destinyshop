package com.gidcode.destinyshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gidcode.destinyshop.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{
    
}
