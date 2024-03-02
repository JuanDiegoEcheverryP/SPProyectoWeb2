package com.example.spaceinvaders.services;

import com.example.spaceinvaders.model.ProductoBodega;
import com.example.spaceinvaders.repository.ProductoBodegaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoBodegaService {

    @Autowired
    private ProductoBodegaRepository productoBodegaRepository;

    public List<ProductoBodega> obtenerTodasLasProductoBodegas() {
        return productoBodegaRepository.findAll();
    }
}
