package com.example.spaceinvaders.services;

import java.util.List;

import com.example.spaceinvaders.model.TipoNave;
import com.example.spaceinvaders.repository.TipoNaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipoNaveService {
    @Autowired
    private TipoNaveRepository tipoNaveRepository;

    public List<TipoNave> listarTipoNaves() {
        return tipoNaveRepository.findAll();
    }
}
