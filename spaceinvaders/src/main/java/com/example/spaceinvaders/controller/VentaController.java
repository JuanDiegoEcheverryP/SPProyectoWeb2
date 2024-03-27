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

import com.example.spaceinvaders.model.DTO.ActualizacionComercioDTO;
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
    private ProductoBodegaService BodegaService;

    @Autowired
    private NaveService naveService;

    @Autowired
    private StockPlanetaService prodcutoService;

    @PutMapping("")
    public Map<String, Object> actualizarPersona(@RequestBody CompraVentaDTO venta) {
        
        ActualizacionComercioDTO resultado=new ActualizacionComercioDTO();
        
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("cantidadTuplasModificadas", resultado);
        return respuesta;
    }

    //PASOS
    //YA SE EVALUO SI SE PODIA COMPRAR ESA CANTIDAD 
    //Y SE EVALUO SI SE PUEDE COMPRAR CON DICHOS CREDITOS
    //ya se valuo si podia comprarlo segun su bodega

    //SE DEBE:
    //actualizar el stock del planeta segun el caso (aumentar o restar)
    //se debe reducir o aumentar la cantidad del producto en la bodega 
        //tambien puede ser el caso que se debe crear el producto en la bodega
        //o que se tenga que borrar
    //se deben reducir o aumentar la cantidad de creditos
}
