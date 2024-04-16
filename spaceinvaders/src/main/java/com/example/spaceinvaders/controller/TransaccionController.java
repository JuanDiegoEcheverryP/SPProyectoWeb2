package com.example.spaceinvaders.controller;


import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.spaceinvaders.model.DTO.CompraVentaDTO;
import com.example.spaceinvaders.model.DTO.RespuestaTransaccionDTO;
import com.example.spaceinvaders.services.NaveService;
import com.example.spaceinvaders.services.ProductoBodegaService;
import com.example.spaceinvaders.services.StockPlanetaService;

@RestController
@RequestMapping("/api/transaccion")
@CrossOrigin(origins = "http://localhost:4200/")
public class TransaccionController {

    Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private ProductoBodegaService bodegaService;

    @Autowired
    private NaveService naveService;

    @Autowired
    private StockPlanetaService stockService;

    @PutMapping("/venta")
    public ResponseEntity<?> procesarVenta(@RequestBody CompraVentaDTO venta) {
        boolean cantidadVenta = bodegaService.validarCantidadVenta(venta.getIdProducto(), venta.getIdNave(), venta.getCantidadProducto());
        
        if (cantidadVenta ) {
            try {
                // Iniciar transacción
                // Actualizar la bodega
                bodegaService.actualizarBodega(venta.getIdNave(), venta.getIdProducto(), venta.getCantidadProducto(), 0);
                // Actualizar el crédito de la nave
                naveService.actualizarCreditos(venta.getIdNave(), venta.getTotal()*-1);
                // Actualizar el stock
                stockService.actualizarStock(venta.getIdProducto(), venta.getIdPlaneta(), venta.getCantidadProducto()*-1);
                // Confirmar transacción
                RespuestaTransaccionDTO mensaje= new RespuestaTransaccionDTO();
                mensaje.setMensaje("Venta exitosa");
                return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);
            } catch (Exception e) {
                // Si hay un error, revertir transacción
                // Registrar el error
                // Devolver mensaje de error
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Error al realizar la venta: " + e.getMessage());
            }
        } else {
            // Devolver mensaje indicando la causa de la falla
           
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("No hay suficientes unidades disponibles");
        }
    }
    
    @PutMapping("/compra")
    public ResponseEntity<?> procesarCompra(@RequestBody CompraVentaDTO compra) {
        System.out.println(compra.getIdPlaneta() +" "+ compra.getIdProducto());
        boolean stockCompra = stockService.validarStockCompra(compra.getIdProducto(), compra.getIdPlaneta(), compra.getCantidadProducto());
        boolean creditoNave = naveService.validarCreditoNave(compra.getIdNave(), compra.getTotal());
        boolean capacidadBodega = naveService.validarCapacidadBodega(compra.getIdNave(), compra.getIdProducto(), compra.getCantidadProducto());
        
        if (stockCompra && creditoNave && capacidadBodega) {
            try {
                // Iniciar transacción
                // Actualizar la bodega
                bodegaService.actualizarBodega(compra.getIdNave(), compra.getIdProducto(), compra.getCantidadProducto(), 1);
                // Actualizar el crédito de la nave
                naveService.actualizarCreditos(compra.getIdNave(), compra.getTotal());
                // Actualizar el stock
                stockService.actualizarStock(compra.getIdProducto(), compra.getIdPlaneta(), compra.getCantidadProducto());
                // Confirmar transacción
                RespuestaTransaccionDTO mensaje= new RespuestaTransaccionDTO();
                mensaje.setMensaje("Compra exitosa!");
                return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);
            } catch (Exception e) {
                // Si hay un error, revertir transacción
                // Registrar el error
                // Devolver mensaje de error
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body( "Error al realizar la compra: " + e.getMessage());
            }
        } else {
            // Devolver mensaje indicando la causa de la falla
            if (!stockCompra) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("No hay suficiente stock disponible");
            } else if (!creditoNave) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("La nave no tiene suficiente crédito");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("La bodega no tiene suficiente capacidad para almacenar el producto");
            }
        }
    }
}
