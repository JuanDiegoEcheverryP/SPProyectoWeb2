package com.example.spaceinvaders.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spaceinvaders.model.Nave;
import com.example.spaceinvaders.model.DTO.NaveDTO;
import com.example.spaceinvaders.services.NaveService;

@RestController
@RequestMapping("/api/nave")
@CrossOrigin(origins = "http://localhost:4200/")
public class NaveController {
    @Autowired
    private NaveService naveService;

    @GetMapping("/list")
    public List<Nave> listarNaves() {
        return naveService.listaNaves();
    } 

    @GetMapping("/ver/{id}")
    public NaveDTO obtenerInfoNave( @PathVariable Long id) {
        
        Nave naveObtenida=naveService.recuperarNave(id);

        NaveDTO respuesta=new NaveDTO();
        respuesta.setNombre(naveObtenida.getNombre());
        respuesta.setTipoNave(naveObtenida.getTipoNave().getNombre());
        respuesta.setCreditos(naveObtenida.getCredito());
        respuesta.setTiempoRestante(naveObtenida.getTiempo());
        respuesta.setCapacidadMaxima(naveObtenida.getTipoNave().getVolBodega());
        respuesta.setFoto(naveObtenida.getTipoNave().getFoto());
        respuesta.setVelocidad(naveObtenida.getTipoNave().getVelocidad());

        //obtener la localizacion
        //fijar estrella
        respuesta.setIdEstrella(naveObtenida.getLocalizacion().getId());
        respuesta.setNombreEstrella(naveObtenida.getLocalizacion().getNombre());
       
        //fijar planeta 
        // fijar planeta
        if (naveObtenida.getLocalizacionPlaneta() != null) {
            respuesta.setIdPlaneta(naveObtenida.getLocalizacionPlaneta().getId());
            respuesta.setNombrePlaneta(naveObtenida.getLocalizacionPlaneta().getNombre());
        } else {
            // handle the case when LocalizacionPlaneta is null
            respuesta.setIdPlaneta(null);
            respuesta.setNombrePlaneta(null);
        }
        //fijar volumen que se ha utilizado
        respuesta.setCapadidadUtilizada(naveService.obtenerVolumenTotal(id));

        return respuesta;
        
    }
    
}
