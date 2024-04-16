package com.example.spaceinvaders.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.spaceinvaders.model.DTO.ProductoDTO;
import com.example.spaceinvaders.model.DTO.productoBodegaDTO;
import com.example.spaceinvaders.services.ProductoBodegaService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/bodega")
@CrossOrigin(origins = "http://localhost:4200/")
public class ProductoBodegaController {
    
    Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private ProductoBodegaService BodegaService;

    @GetMapping("nave/{idNave}/producto/{idProducto}/planeta/{idPlaneta}")
    public ResponseEntity<?> recuperarProductosXBodega(@PathVariable Long idPlaneta, @PathVariable Long idProducto,@PathVariable Long idNave) {
        try {
            ProductoDTO producto = BodegaService.recuperarProductoXBodega(idPlaneta,idProducto,idNave);
            return ResponseEntity.ok().body(producto);
        } 
        catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existen productos en el planeta que esten en la bodega de la nave");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerBodegaPorNaveId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(BodegaService.buscarProductosPorBodega(id));
        } 
        catch (NoSuchElementException e)
        {
            String errorMessage = "No existe un planeta para esa nave con dicho id";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        } 
    }

    @GetMapping("/listarBodega/{id}")
    public List<productoBodegaDTO> obtenerBodegaDTOPorNaveId(@PathVariable Long id) {
        return BodegaService.buscarProductosDTOPorBodega(id);
    }

    @GetMapping("/ProductoBodegaExist/{idNave}/{idProducto}")
    public boolean obtenerProductoBodega(@PathVariable Long idNave,@PathVariable Long idProducto) {
        return BodegaService.obtenerProductoBodega(idNave,idProducto);
    }

}


