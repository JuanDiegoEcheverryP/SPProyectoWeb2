package com.example.spaceinvaders.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spaceinvaders.model.Avatar;
import com.example.spaceinvaders.model.Producto;
import com.example.spaceinvaders.services.AvatarService;
import com.example.spaceinvaders.services.ProductoService;

@RestController
@RequestMapping("/api/producto")
@CrossOrigin(origins = "http://localhost:4200/")
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @GetMapping("/list")
    public List<Producto> listarProductos() {
        return productoService.listaProductos();
    } 
}
