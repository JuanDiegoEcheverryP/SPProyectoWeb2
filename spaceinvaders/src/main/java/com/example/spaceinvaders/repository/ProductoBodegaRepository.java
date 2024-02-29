package com.example.spaceinvaders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.spaceinvaders.model.Producto_bodega;


@Repository
public interface ProductoBodegaRepository extends JpaRepository<Producto_bodega, Long> {
    
}
