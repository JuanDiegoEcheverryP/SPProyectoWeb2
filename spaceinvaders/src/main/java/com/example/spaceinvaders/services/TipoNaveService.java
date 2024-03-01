package com.example.spaceinvaders.services;

import com.example.spaceinvaders.model.TipoNave;
import com.example.spaceinvaders.repository.TipoNaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoNaveService {

    private final TipoNaveRepository tipoNaveRepository;

    @Autowired
    public TipoNaveService(TipoNaveRepository tipoNaveRepository) {
        this.tipoNaveRepository = tipoNaveRepository;
    }

    public List<TipoNave> getAll() {
        return tipoNaveRepository.findAll();
    }

    public TipoNave getById(Long id) {
        Optional<TipoNave> optionalTipoNave = tipoNaveRepository.findById(id);
        return optionalTipoNave.orElse(null);
    }

    public TipoNave create(TipoNave tipoNave) {
        return tipoNaveRepository.save(tipoNave);
    }

    public TipoNave update(Long id, TipoNave newTipoNaveData) {
        Optional<TipoNave> optionalTipoNave = tipoNaveRepository.findById(id);
        if (optionalTipoNave.isPresent()) {
            TipoNave existingTipoNave = optionalTipoNave.get();
            return tipoNaveRepository.save(existingTipoNave);
        } else {
            return null;
        }
    }

    public void delete(Long id) {
        tipoNaveRepository.deleteById(id);
    }
}
