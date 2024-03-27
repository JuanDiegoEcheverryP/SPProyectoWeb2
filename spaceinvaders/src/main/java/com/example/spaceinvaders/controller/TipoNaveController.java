package com.example.spaceinvaders.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spaceinvaders.model.TipoNave;
import com.example.spaceinvaders.services.TipoNaveService;

@RestController
@RequestMapping("/api/tipoNave")
@CrossOrigin(origins = "http://localhost:4200/")
public class TipoNaveController {
    
    @Autowired
    private TipoNaveService tipoNaveService;

    @GetMapping("/list")
    public List<TipoNave> listarAvatares() {
        return tipoNaveService.listarTipoNaves();
    } 
}
