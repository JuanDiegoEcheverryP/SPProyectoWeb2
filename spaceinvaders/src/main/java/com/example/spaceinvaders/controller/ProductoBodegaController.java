package com.example.spaceinvaders.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spaceinvaders.model.DTO.ProductoDTO;
import com.example.spaceinvaders.services.PlanetaService;
import com.example.spaceinvaders.services.ProductoBodegaService;
import com.example.spaceinvaders.services.ProductoService;
import com.example.spaceinvaders.services.StockPlanetaService;

@RestController
@RequestMapping("/api/bodega")
public class ProductoBodegaController {
    
    Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private ProductoBodegaService BodegaService;

    @GetMapping("{idBodega}/producto/{idProducto}/planeta/{idPlaneta}")
    public ResponseEntity<ProductoDTO> recuperarProductosXPlaneta(@PathVariable Long idPlaneta, @PathVariable Long idProducto,@PathVariable Long idBodega) {
        
        ProductoDTO producto = BodegaService.recuperarProductoXBodega(idPlaneta,idProducto,idBodega);
        return ResponseEntity.ok().body(producto);
       
        /*try {
            List<ProductoDTO> productos = stockPlanetaService.recuperarStockDePlaneta(idPlaneta);
            return ResponseEntity.ok().body(productos);
        } catch (InvalidIdException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }*/
    }



}


