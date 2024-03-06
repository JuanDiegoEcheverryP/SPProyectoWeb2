package com.example.spaceinvaders.services;

import org.springframework.stereotype.Service;

import com.example.spaceinvaders.model.Estrella;
import com.example.spaceinvaders.model.Nave;
import com.example.spaceinvaders.model.Planeta;
import com.example.spaceinvaders.repository.CaminoRepository;
import com.example.spaceinvaders.repository.EstrellaRepository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class EstrellaService {
    Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private EstrellaRepository estrellaRepository;

    @Autowired
    private CaminoService caminoService;

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
        return estrellaRepository.findAllByNombreContainingIgnoreCase(textoBusqueda);
    }

    public List<Estrella> buscarEstrellasQueTerminenCon(String textoBusqueda) {
        return estrellaRepository.findAllByNombreEndingWithIgnoreCase(textoBusqueda);
    }

    public List<Estrella> buscarEstrellasQueEmpiecenCon(String textoBusqueda) {
        return estrellaRepository.findAllByNombreContainingIgnoreCase(textoBusqueda);
    }

    public String estrellaValidationNombre(Estrella estrella)
    {
        String mensaje="";
        List<Estrella> estrellaEvalNombre=estrellaRepository.findAllByNombre(estrella.getNombre());

       if(!estrellaEvalNombre.isEmpty() && estrellaEvalNombre.get(0).getId()!=estrella.getId())
        {
            mensaje="Ya existe una estrella con ese nombre";
        }

        return mensaje;
    }

    public String estrellaValidationCoord(Estrella estrella)
    {
        String mensaje="";
        List<Estrella> estrellaEvalCoord=estrellaRepository.findByCoords(estrella.getCoord_x(), estrella.getCoord_y(), estrella.getCoord_z());

       if( !estrellaEvalCoord.isEmpty() && estrellaEvalCoord.get(0).getId()!=estrella.getId())
        {
            mensaje="Una estrella ya tiene esas coordenadas";
        }

        return mensaje;
    }

    public String estrellaValidationNaves(Estrella estrella)
    {
        List<Nave> naves=estrellaRepository.findNavesByEstrellaId(estrella.getId());
        
        String mensaje="";

        if(!naves.isEmpty())
        {
            mensaje="La estrella no se puede borrar porque existen naves en la estrella";
        }

        //espera el mensaje de planetas service

        return mensaje;
    }

    public Estrella crearEstrella(Estrella estrella) {

        Estrella nueva=estrellaRepository.save(estrella);
        caminoService.nuevoCaminoUnaEstrella(estrella);
        return nueva;
    }
}
