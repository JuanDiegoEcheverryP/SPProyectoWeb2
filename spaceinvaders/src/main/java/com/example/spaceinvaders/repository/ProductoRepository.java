package com.example.spaceinvaders.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.spaceinvaders.model.Producto;


@Repository
public interface ProductoRepository  extends JpaRepository<Producto, Long> {

    List<Producto> findAllByNombre(String text);

    List<Producto> findAllByNombreStartingWithIgnoreCase(String text);

    List<Producto> findAllByNombreEndingWithIgnoreCase(String text);

    List<Producto> findAllByNombreContainingIgnoreCase(String text);
    

    
}
