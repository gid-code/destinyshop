package com.gidcode.destinyshop.repository;

import com.gidcode.destinyshop.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
