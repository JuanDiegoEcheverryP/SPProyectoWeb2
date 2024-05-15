package com.example.spaceinvaders.services;

import com.example.spaceinvaders.model.Jugador;
import com.example.spaceinvaders.model.Nave;
import com.example.spaceinvaders.model.DTO.UsuarioDTO;
import com.example.spaceinvaders.model.Enum.Rol;
import com.example.spaceinvaders.repository.JugadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JugadorService {

    @Autowired
    private JugadorRepository jugadorRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

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
        jwtService.generateToken(jugador);
        return nueva;
    }

    public UsuarioDTO obtenerJugadorXUsuarioXContrasena(String nombre, String contrasena)
    {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(nombre, contrasena));
        List<Jugador> jugador = jugadorRepository.findJugadorByContrasenAndNombre(nombre,contrasena);
        UsuarioDTO usuario=new UsuarioDTO();

        if(jugador.size()!=0)
        {
            String jwt = jwtService.generateToken(jugador.get(0));
            usuario.setAvatar(jugador.get(0).getAvatar().getImagen());
            usuario.setNombre(jugador.get(0).getNombre());
            usuario.setRol(jugador.get(0).getRol());
            
            if(jugador.get(0).getRol()!=null)
            {
                usuario.setIdNave(jugador.get(0).getNaveJuego().getId());
            }
            else
            {
                usuario.setIdNave(null);
            }

            usuario.setId(jugador.get(0).getId());
            usuario.setToken(jwt);
        }
       
        return usuario;
    }

    public Nave obtenerNavePorJugadorId(Long id) {
        return jugadorRepository.findNavePorJugadorId(id);
    }

    public void actualizarRolJugador(Long id, Rol rol)
    {
        jugadorRepository.actualizarRol(rol, id);
    }

    public Long actualizarNaveJugador(Long id, Long naveid)
    {
        Nave nave=jugadorRepository.findNave(naveid);
        System.out.println(nave);
        jugadorRepository.actualizarNave(nave, id);

        return nave.getId();
    }
}
