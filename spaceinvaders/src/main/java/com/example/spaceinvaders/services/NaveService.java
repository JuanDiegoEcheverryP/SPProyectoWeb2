package com.example.spaceinvaders.services;

import com.example.spaceinvaders.model.Nave;
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

    public Nave recuperarNave(Long id)
    {
        return naveRepository.findById(id).orElseThrow();
    }

    public Nave guardarNave(Nave nave)
    {
        return naveRepository.save(nave);
    }

    public void borrarNave(Nave nave)
    {
        naveRepository.delete(nave);
    }

    public List<Nave> buscarNombre(String textoBusqueda) {
        return naveRepository.findAllByNombre(textoBusqueda);
    }

    public List<Nave> buscarNavesQueContengan(String textoBusqueda) {
        return naveRepository.findAllByNombreStartingWithIgnoreCase(textoBusqueda);
    }

    public List<Nave> buscarNavesQueTerminenCon(String textoBusqueda) {
        return naveRepository.findAllByNombreEndingWithIgnoreCase(textoBusqueda);
    }

    public List<Nave> buscarNavesQueEmpiecenCon(String textoBusqueda) {
        return naveRepository.findAllByNombreContainingIgnoreCase(textoBusqueda);
    }

}
