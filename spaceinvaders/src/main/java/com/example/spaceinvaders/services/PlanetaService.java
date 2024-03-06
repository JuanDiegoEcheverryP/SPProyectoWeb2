package com.example.spaceinvaders.services;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spaceinvaders.model.Planeta;
import com.example.spaceinvaders.model.Nave;
import com.example.spaceinvaders.repository.PlanetaRepository;

@Service
public class PlanetaService {
    Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private PlanetaRepository planetaRepository;

    public List<Planeta> listaPlanetas()
    {
        return planetaRepository.findAllByHabitadoTrue();
    }

    public List<Planeta> listaTodos()
    {
        return planetaRepository.findAll();
    }

    public Planeta recuperarPlaneta(Long id)
    {
        return planetaRepository.findById(id).orElseThrow();
    }

    public Planeta guardarPlaneta(Planeta planeta)
    {

        return planetaRepository.save(planeta);
    }

    public void borrarPlaneta(Planeta planeta)
    {
        planetaRepository.delete(planeta);
    }

    public List<Planeta> buscarNombre(String textoBusqueda) {
        return planetaRepository.findAllByNombre(textoBusqueda);
    }

    public List<Planeta> buscarPlanetasQueContengan(String textoBusqueda) {
        return planetaRepository.findAllByNombreContainingIgnoreCase(textoBusqueda);
    }

    public List<Planeta> buscarPlanetasQueTerminenCon(String textoBusqueda) {
        return planetaRepository.findAllByNombreEndingWithIgnoreCase(textoBusqueda);
    }

    public List<Planeta> buscarPlanetasQueEmpiecenCon(String textoBusqueda) {
        return planetaRepository.findAllByNombreContainingIgnoreCase(textoBusqueda);
    }

    public String planetaValidationNombre(Planeta planeta)
    {
        String mensaje="";
        List<Planeta> planetaEvalNombre=planetaRepository.findAllByNombre(planeta.getNombre());

       if(!planetaEvalNombre.isEmpty() && planetaEvalNombre.get(0).getId()!=planeta.getId())
        {
            mensaje="Ya existe una Planeta con ese nombre";
        }

        return mensaje;
    }

    public Planeta crearPlaneta(Planeta planeta) 
    {
        planeta.setImagen("https://shorturl.at/kuFO8");
        Planeta nueva=planetaRepository.save(planeta);
        return nueva;
    }
}
