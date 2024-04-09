package com.example.spaceinvaders.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spaceinvaders.model.Camino;
import com.example.spaceinvaders.model.Estrella;
import com.example.spaceinvaders.model.DTO.EstrellaDTO;
import com.example.spaceinvaders.services.CaminoService;

@RestController
@RequestMapping("/api/camino")
@CrossOrigin(origins = "http://localhost:4200/")
public class CaminoController {

    @Autowired
    private CaminoService caminoService;

    @GetMapping("/estrella/{id}")
    public List<Camino> listarCaminos(@PathVariable Long id) {
        List<Camino> a =caminoService.listarCaminos(id);
        return a;
    }

    @GetMapping("/inicioFinal/{idInicio}/{idFinal}")
    public Camino obtenerCaminoDosEstrellas(@PathVariable Long idInicio,@PathVariable Long idFinal) {
        return caminoService.obtenerCaminoDosEstrellas(idInicio,idFinal);
    }

    @GetMapping("/estrellaFinal/{id}")
    public List<Estrella> obtenerEstrellaFinalPorEstrellaInicioId(@PathVariable Long id) {
        return caminoService.obtenerEstrellaFinalPorEstrellaInicioId(id);
    }

    @GetMapping("/estrellasConectadas/{id}")
    public List<EstrellaDTO> obtenerEstrellasConectadasPorEstrellaInicioId(@PathVariable Long id) {
        return caminoService.obtenerEstrellasConectadasPorEstrellaInicioId(id);
        
    }
}
