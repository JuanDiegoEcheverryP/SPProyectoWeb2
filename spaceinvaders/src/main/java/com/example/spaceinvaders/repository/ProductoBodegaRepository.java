package com.proyecto.spaceinvaders.NaveEspacial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.spaceinvaders.NaveEspacial.model.Producto_bodega;


@Repository
public interface ProductoBodegaRepository extends JpaRepository<Producto_bodega, Long> {
    
}
