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
@RequestMapping("/api/venta")
@CrossOrigin(origins = "http://localhost:4200/")
public class VentaController {

    Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private ProductoBodegaService bodegaService;

    @Autowired
    private NaveService naveService;

    @Autowired
    private StockPlanetaService stockService;

    @PutMapping("")
    public String procesarVenta(@RequestBody CompraVentaDTO venta) {
        boolean cantidadVenta = bodegaService.validarCantidadVenta(venta.getIdproducto(), venta.getIdNave(), venta.getCantidadProducto());
        
        if (cantidadVenta ) {
            try {
                // Iniciar transacción
                // Actualizar la bodega
                bodegaService.actualizarBodega(venta.getIdNave(), venta.getIdproducto(), venta.getCantidadProducto(), 0);
                // Actualizar el crédito de la nave
                naveService.actualizarCreditos(venta.getIdNave(), venta.getTotal()*-1);
                // Actualizar el stock
                stockService.actualizarStock(venta.getIdproducto(), venta.getIdPlaneta(), venta.getCantidadProducto()*-1);
                // Confirmar transacción
                return "Venta exitosa";
            } catch (Exception e) {
                // Si hay un error, revertir transacción
                // Registrar el error
                // Devolver mensaje de error
                return "Error al realizar la venta: " + e.getMessage();
            }
        } else {
            // Devolver mensaje indicando la causa de la falla
           
                return "No hay suficientes unidades disponibles";
        }
    }
    
    
}
