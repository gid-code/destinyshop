package com.gidcode.destinyshop.repository;

import com.gidcode.destinyshop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.ListCrudRepository;

public interface CategoryRepository extends ListCrudRepository<Category, Long> {
    Category findByName(String name);

    boolean existsByName(String name);
}
