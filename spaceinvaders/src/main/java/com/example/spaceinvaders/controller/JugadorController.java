package com.example.spaceinvaders.controller;

import com.example.spaceinvaders.model.Jugador;
import com.example.spaceinvaders.services.JugadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/jugador")
public class JugadorController {

    @Autowired
    private JugadorService jugadorService;

    @GetMapping("/list")
    public String listarJugadors(Model model) {
        List<Jugador> jugadors = jugadorService.obtenerTodasLasJugadors();
        model.addAttribute("jugadores", jugadors);
        return "Jugador_CRUD/Jugador-list";
    }
}
