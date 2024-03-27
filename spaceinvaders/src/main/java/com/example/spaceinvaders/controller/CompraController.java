package com.example.spaceinvaders.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.spaceinvaders.model.DTO.CompraVentaDTO;
import com.example.spaceinvaders.services.NaveService;
import com.example.spaceinvaders.services.ProductoBodegaService;
import com.example.spaceinvaders.services.StockPlanetaService;

@RestController
@RequestMapping("/api/compra")
@CrossOrigin(origins = "http://localhost:4200/")
public class CompraController {

    Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private ProductoBodegaService bodegaService;

    @Autowired
    private NaveService naveService;

    @Autowired
    private StockPlanetaService stockService;

    @PutMapping("")
    public String procesarCompra(@RequestBody CompraVentaDTO compra) {
        boolean stockCompra = stockService.validarStockCompra(compra.getIdproducto(), compra.getIdPlaneta(), compra.getCantidadProducto());
        boolean creditoNave = naveService.validarCreditoNave(compra.getIdNave(), compra.getTotal());
        boolean capacidadBodega = naveService.validarCapacidadBodega(compra.getIdNave(), compra.getIdproducto(), compra.getCantidadProducto());
        
        if (stockCompra && creditoNave && capacidadBodega) {
            try {
                // Iniciar transacción
                // Actualizar la bodega
                bodegaService.actualizarBodega(compra.getIdNave(), compra.getIdproducto(), compra.getCantidadProducto(), 1);
                // Actualizar el crédito de la nave
                naveService.actualizarCreditos(compra.getIdNave(), compra.getTotal());
                // Actualizar el stock
                stockService.actualizarStock(compra.getIdproducto(), compra.getIdPlaneta(), compra.getCantidadProducto());
                // Confirmar transacción
                return "Compra exitosa";
            } catch (Exception e) {
                // Si hay un error, revertir transacción
                // Registrar el error
                // Devolver mensaje de error
                return "Error al realizar la compra: " + e.getMessage();
            }
        } else {
            // Devolver mensaje indicando la causa de la falla
            if (!stockCompra) {
                return "No hay suficiente stock disponible";
            } else if (!creditoNave) {
                return "La nave no tiene suficiente crédito";
            } else {
                return "La bodega no tiene suficiente capacidad para almacenar el producto";
            }
        }
    }
}
