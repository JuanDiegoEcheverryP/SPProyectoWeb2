package com.example.spaceinvaders.services;

import com.example.spaceinvaders.model.Jugador;
import com.example.spaceinvaders.repository.JugadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JugadorService {

    @Autowired
    private JugadorRepository jugadorRepository;

    public List<Jugador> obtenerTodasLasJugadors() {
        return jugadorRepository.findAll();
    }
}
