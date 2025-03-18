package com.gidcode.destinyshop.repository;

import com.gidcode.destinyshop.model.Image;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findProductById(Long id);
}
