package com.example.spaceinvaders.services;

import org.springframework.stereotype.Service;

import com.example.spaceinvaders.model.Estrella;
import com.example.spaceinvaders.repository.CaminoRepository;
import com.example.spaceinvaders.repository.EstrellaRepository;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class EstrellaService {
    
    @Autowired
    private EstrellaRepository estrellaRepository;

    @Autowired
    private CaminoRepository caminoRepository;

    public Estrella buscarEstrella()
    {
        Estrella nueva=new Estrella();

        return nueva;
    }

    public List<Estrella> listaEstrellas()
    {
        return estrellaRepository.findAll();
    }

    public Estrella recuperarEstrella(Long id)
    {
        return estrellaRepository.findById(id).orElseThrow();
    }

    public Estrella guardarEstrella(Estrella estrella)
    {
        return estrellaRepository.save(estrella);
    }

    public void borrarEstrella(Estrella estrella)
    {
        estrellaRepository.delete(estrella);
    }

    public List<Estrella> buscarNombre(String textoBusqueda) {
        return estrellaRepository.findAllByNombre(textoBusqueda);
    }

    public List<Estrella> buscarEstrellasQueContengan(String textoBusqueda) {
        return estrellaRepository.findAllByNombreStartingWithIgnoreCase(textoBusqueda);
    }

    public List<Estrella> buscarEstrellasQueTerminenCon(String textoBusqueda) {
        return estrellaRepository.findAllByNombreEndingWithIgnoreCase(textoBusqueda);
    }

    public List<Estrella> buscarEstrellasQueEmpiecenCon(String textoBusqueda) {
        return estrellaRepository.findAllByNombreContainingIgnoreCase(textoBusqueda);
    }
}
