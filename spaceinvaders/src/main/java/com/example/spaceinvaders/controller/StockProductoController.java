package com.example.spaceinvaders.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spaceinvaders.model.DTO.ProductoDTO;
import com.example.spaceinvaders.services.StockPlanetaService;

import jakarta.persistence.EntityNotFoundException;

//import jakarta.persistence.EntityNotFoundException;


@RestController
@RequestMapping("/api/stock")
@CrossOrigin(origins = "http://localhost:4200/")
public class StockProductoController {
     
    Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private StockPlanetaService stockPlanetaService;

    // http://localhost:8080/api/stock/planeta/ID
    @Operation(summary = "Buscar productos que tiene planeta por ID")
    @Secured({ "capitan", "comerciante" })
    @GetMapping("/planeta/{idPlaneta}")
    public List<ProductoDTO> recuperarProductosXPlaneta(@PathVariable Long idPlaneta) {
        return stockPlanetaService.recuperarStockDePlaneta(idPlaneta);
    }

    // http://localhost:8080/api/stock/planeta/ID/producto/ID
    
    @Operation(summary = "Buscar producto por ID del planeta ID")
    @GetMapping("planeta/{idPlaneta}/producto/{idProducto}")
    public ResponseEntity<?> recuperarProductosXPlaneta(@PathVariable Long idPlaneta, @PathVariable Long idProducto) {
       
        try {
            ProductoDTO producto = stockPlanetaService.recuperarProductoXPlaneta(idPlaneta,idProducto);
            return ResponseEntity.ok().body(producto);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existen productos en el stock del planeta");
        }
    }
    
}
