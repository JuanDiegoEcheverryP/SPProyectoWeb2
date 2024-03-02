package com.example.spaceinvaders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.spaceinvaders.model.ProductoBodega;

@Repository
public interface ProductoBodegaRepository extends JpaRepository<ProductoBodega, Long> {
    
}
