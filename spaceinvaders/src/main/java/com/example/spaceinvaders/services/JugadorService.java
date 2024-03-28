package com.example.spaceinvaders.services;

import com.example.spaceinvaders.model.Estrella;
import com.example.spaceinvaders.model.Jugador;
import com.example.spaceinvaders.model.Nave;
import com.example.spaceinvaders.model.DTO.JugadorLogIn;
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

    public Jugador recuperarJugador(Long id)
    {
        return jugadorRepository.findById(id).orElseThrow();
    }

    public Jugador guardarJugador(Jugador jugador)
    {
        return jugadorRepository.save(jugador);
    }

    public void borrarJugador(Jugador jugador)
    {
        jugadorRepository.delete(jugador);
    }

    public List<Jugador> buscarNombre(String textoBusqueda) {
        return jugadorRepository.findAllByNombre(textoBusqueda);
    }

    public List<Jugador> buscarJugadorsQueContengan(String textoBusqueda) {
        return jugadorRepository.findAllByNombreStartingWithIgnoreCase(textoBusqueda);
    }

    public List<Jugador> buscarJugadorsQueTerminenCon(String textoBusqueda) {
        return jugadorRepository.findAllByNombreEndingWithIgnoreCase(textoBusqueda);
    }

    public List<Jugador> buscarJugadorsQueEmpiecenCon(String textoBusqueda) {
        return jugadorRepository.findAllByNombreContainingIgnoreCase(textoBusqueda);
    }

    public String jugadorValidationNombre(Jugador jugador)
    {
        String mensaje="";
        List<Jugador> jugadorEvalNombre=jugadorRepository.findAllByNombre(jugador.getNombre());

        if(!jugadorEvalNombre.isEmpty() && jugadorEvalNombre.get(0).getId()!=jugador.getId())
        {
            mensaje="Ya existe un jugador con ese nombre";
        }

        return mensaje;
    }

    public Jugador crearJugador(Jugador jugador) {

        Jugador nueva=jugadorRepository.save(jugador);
        return nueva;
    }

    public List<Jugador> obtenerJugadorXUsuarioXContrasena(JugadorLogIn jugador)
    {
        return jugadorRepository.findJugadorByContrasenAndNombre(jugador.getNombre(),jugador.getContrasena());
    }

    public Nave obtenerNavePorJugadorId(Long id) {
        return jugadorRepository.findNavePorJugadorId(id);
    }
}
