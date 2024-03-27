package com.example.spaceinvaders.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spaceinvaders.model.Nave;
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

    
}
