package com.example.spaceinvaders.services;

import com.example.spaceinvaders.model.Nave;
import com.example.spaceinvaders.repository.NaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NaveService {

    @Autowired
    private NaveRepository naveRepository;

    public List<Nave> obtenerTodasLasNaves() {
        return naveRepository.findAll();
    }

    //ESTA FUNCION LA NECESITO PARA PODER BORRAR UN PLANETA
}
