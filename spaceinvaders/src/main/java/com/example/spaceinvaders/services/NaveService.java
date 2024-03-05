package com.example.spaceinvaders.services;

import com.example.spaceinvaders.model.Estrella;
import com.example.spaceinvaders.model.Jugador;
import com.example.spaceinvaders.model.Nave;
import com.example.spaceinvaders.model.Nave;
import com.example.spaceinvaders.repository.JugadorRepository;
import com.example.spaceinvaders.repository.NaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NaveService {

    @Autowired
    private NaveRepository naveRepository;

    @Autowired
    private JugadorRepository jugadorRepository;

    public List<Nave> listaNaves() {
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

    public String naveValidationNombre(Nave nave)
    {
        String mensaje="";
        List<Nave> naveEvalNombre=naveRepository.findAllByNombre(nave.getNombre());

        if(!naveEvalNombre.isEmpty() && naveEvalNombre.get(0).getId()!=nave.getId())
        {
            mensaje="Ya existe una nave con ese nombre";
        }

        return mensaje;
    }

    public String naveValidarBorrar(Nave nave) {
        String mensaje="";

        List<Jugador> jugadorEval = jugadorRepository.findJugadoresByNaveId(nave.getId());

        if(!jugadorEval.isEmpty()){
            mensaje= "Hay usuarios en esa nave";
        }
        return mensaje;
    }

    public Nave crearNave(Nave nave) {
        Nave nueva=naveRepository.save(nave);
        return nueva;
    }
}
