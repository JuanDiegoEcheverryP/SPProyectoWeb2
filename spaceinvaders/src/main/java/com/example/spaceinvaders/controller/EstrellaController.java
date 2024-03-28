package com.example.spaceinvaders.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spaceinvaders.model.Estrella;
import com.example.spaceinvaders.model.Nave;
import com.example.spaceinvaders.model.Planeta;
import com.example.spaceinvaders.model.ProductoBodega;
import com.example.spaceinvaders.services.EstrellaService;

@RestController
@RequestMapping("/api/estrella")
@CrossOrigin(origins = "http://localhost:4200/")
public class EstrellaController {
    @Autowired
    private EstrellaService estrellaService;
    
    @GetMapping("/list")
    public List<Estrella> listarEstrellas() {
        return estrellaService.listaEstrellas();
    } 

    @GetMapping("/planetas/{id}")
    public List<Planeta> listarPlanetasPorEstrella(@PathVariable Long id) {
        return estrellaService.listaPlanetasEstrella(id);
    }

    @GetMapping("/{id}")
    public Estrella obtenerEstrellaPorId(@PathVariable Long id) {
        return estrellaService.recuperarEstrella(id);
    }

    @GetMapping("/naves/{id}")
    public List<Nave> obtenerNavesPorEstrellaId(@PathVariable Long id) {
        return estrellaService.buscarNavesPorEstrellaId(id);
    }
}
