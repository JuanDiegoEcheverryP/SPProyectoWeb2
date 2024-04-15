package com.example.spaceinvaders.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spaceinvaders.model.ProductoBodega;

import com.example.spaceinvaders.model.DTO.ProductoDTO;
import com.example.spaceinvaders.model.DTO.productoBodegaDTO;
import com.example.spaceinvaders.services.ProductoBodegaService;

@RestController
@RequestMapping("/api/bodega")
@CrossOrigin(origins = "http://localhost:4200/")
public class ProductoBodegaController {
    
    Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private ProductoBodegaService BodegaService;

    @GetMapping("nave/{idNave}/producto/{idProducto}/planeta/{idPlaneta}")
    public ResponseEntity<ProductoDTO> recuperarProductosXBodega(@PathVariable Long idPlaneta, @PathVariable Long idProducto,@PathVariable Long idNave) {
        
        ProductoDTO producto = BodegaService.recuperarProductoXBodega(idPlaneta,idProducto,idNave);
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

    @GetMapping("/{id}")
    public List<ProductoBodega> obtenerBodegaPorNaveId(@PathVariable Long id) {
        return BodegaService.buscarProductosPorBodega(id);
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


