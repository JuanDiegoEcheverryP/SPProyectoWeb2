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

import com.example.spaceinvaders.model.Stock_planeta;
import com.example.spaceinvaders.model.DTO.ProductoDTO;
import com.example.spaceinvaders.services.PlanetaService;
import com.example.spaceinvaders.services.ProductoService;
import com.example.spaceinvaders.services.StockPlanetaService;

import jakarta.persistence.EntityNotFoundException;


@RestController
@RequestMapping("/api/stock")
@CrossOrigin(origins = "http://localhost:4200/")
public class StockProductoController {
     
    Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private StockPlanetaService stockPlanetaService;

    // http://localhost:8080/api/stock/planeta/ID
    @Operation(summary = "Buscar productos que tiene planeta por ID")
   /*@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Encontró la persona", content = {
                    @Content(mediaType = "application/json", schema = DbSchema(implementation = Stock_planeta.class)) }),
            @ApiResponse(responseCode = "400", description = "Id suministrado es invalido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Persona no encontrada", content = @Content) })
   */
    @GetMapping("/planeta/{idPlaneta}")
    public List<ProductoDTO> recuperarProductosXPlaneta(@PathVariable Long idPlaneta) {
        return stockPlanetaService.recuperarStockDePlaneta(idPlaneta);
    }

    // http://localhost:8080/api/stock/planeta/ID/producto/ID
    @Operation(summary = "Buscar producto por ID del planeta ID")
    /*@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductoDTO.class))),
            @ApiResponse(responseCode = "400", description = "ID de planeta o producto inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Planeta no encontrado", content = @Content)
    })*/
    @GetMapping("planeta/{idPlaneta}/producto/{idProducto}")
    public ResponseEntity<ProductoDTO> recuperarProductosXPlaneta(@PathVariable Long idPlaneta, @PathVariable Long idProducto) {
        
        ProductoDTO producto = stockPlanetaService.recuperarProductoXPlaneta(idPlaneta,idProducto);
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
