package com.example.spaceinvaders.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/jugador")
@CrossOrigin(origins = "http://localhost:4200/")
public class JuagadorController {

    //buscar jugador para iniciar sesion
    //este retorna el jugador, exceptuando su contrasena

}
