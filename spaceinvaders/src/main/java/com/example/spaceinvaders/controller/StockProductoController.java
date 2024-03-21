package com.example.spaceinvaders.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spaceinvaders.model.Avatar;
import com.example.spaceinvaders.model.Stock_planeta;
import com.example.spaceinvaders.services.PlanetaService;
import com.example.spaceinvaders.services.ProductoService;
import com.example.spaceinvaders.services.StockPlanetaService;

@RestController
@RequestMapping("/api/stock")
public class StockProductoController {

         
    Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private StockPlanetaService stockPlanetaService;

    @Autowired
    private PlanetaService planetaService;

    @Autowired
    private ProductoService productoService;
    
    // http://localhost:8080/api/stock/planeta/ID
    @Operation(summary = "Buscar productos que tiene planeta por ID")
   /*@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Encontró la persona", content = {
                    @Content(mediaType = "application/json", schema = DbSchema(implementation = Stock_planeta.class)) }),
            @ApiResponse(responseCode = "400", description = "Id suministrado es invalido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Persona no encontrada", content = @Content) })
   */
    @GetMapping("planeta/{idPlaneta}")
    public List<Stock_planeta> recuperarPersona(@PathVariable Long idPlaneta) {
        return stockPlanetaService.recuperarStockDePlaneta(idPlaneta);
    }
    
}
