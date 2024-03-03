package com.example.spaceinvaders.controller;

import com.example.spaceinvaders.model.Nave;
import com.example.spaceinvaders.services.NaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/nave")
public class NaveController {

    @Autowired
    private NaveService naveService;

    @GetMapping("/list")
    public String listarNaves(Model model) {
        List<Nave> naves = naveService.obtenerTodasLasNaves();
        model.addAttribute("naves", naves);
        return "Nave_CRUD/Nave-list"; // Devuelve el nombre de la plantilla HTML
    }
}
