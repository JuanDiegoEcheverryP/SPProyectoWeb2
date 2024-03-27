package com.example.spaceinvaders.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spaceinvaders.services.EstrellaService;

@RestController
@RequestMapping("/api/estrella")
@CrossOrigin(origins = "http://localhost:4200/")
public class EstrellaController {
    @Autowired
    private EstrellaService estrellaService;
    
    
}
