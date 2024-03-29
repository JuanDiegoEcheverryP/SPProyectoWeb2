package com.example.spaceinvaders.controller;

import java.util.HashMap;
import java.util.List; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spaceinvaders.model.Jugador;
import com.example.spaceinvaders.model.Nave;
import com.example.spaceinvaders.model.DTO.JugadorLogInDTO;
import com.example.spaceinvaders.model.DTO.PatchRolNave;
import com.example.spaceinvaders.model.DTO.UsuarioDTO;
import com.example.spaceinvaders.services.JugadorService;

@RestController
@RequestMapping("/api/jugador")
@CrossOrigin(origins = "http://localhost:4200/")
public class JuagadorController {

    @Autowired
    private JugadorService jugadorService;

    //buscar jugador para iniciar sesion
    //este retorna el jugador, exceptuando su contrasena
    @PutMapping("login")
    public ResponseEntity<?> iniciarSesion(@RequestBody JugadorLogInDTO jugador) {
       
        
        List<Jugador> encontrado=jugadorService.obtenerJugadorXUsuarioXContrasena(jugador.getNombre(),jugador.getContrasena());
        UsuarioDTO usuario=new UsuarioDTO();
        
        if(encontrado.size()!=0)
        {
            usuario.setAvatar(encontrado.get(0).getAvatar().getImagen());
            usuario.setNombre(encontrado.get(0).getNombre());
            usuario.setRol(encontrado.get(0).getRol());
            usuario.setIdNave(encontrado.get(0).getNaveJuego().getId());
        }
        else
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Contraseña o nombre de usuario incorrecto");
        }

        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/nave/{id}")
    public Nave obtenerNavePorJugadorId(@PathVariable Long id) {
        return jugadorService.obtenerNavePorJugadorId(id);
    }

    @PostMapping("registro")
    public ResponseEntity<?> registrarse(@RequestBody Jugador jugador) {
        UsuarioDTO usuario = new UsuarioDTO();
        
        try {
            jugadorService.crearJugador(jugador);
            usuario.setAvatar(jugador.getAvatar().getImagen());
            usuario.setNombre(jugador.getNombre());
            usuario.setIdNave(null);
            return ResponseEntity.ok(usuario);
        } catch (DataIntegrityViolationException e) {
            // El nombre de jugador ya existe, manejar el error aquí
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("El nombre de usuario ya está en uso");
        }
    } 

    @PatchMapping("{id}/rol/nave")
    public UsuarioDTO modificarRolYNave(@PathVariable Long id, @RequestBody PatchRolNave actualizacion) {
        Jugador jugador=jugadorService.recuperarJugador(id);

        jugadorService.actualizarRolJugador(id,actualizacion.getRol());

        jugadorService.actualizarNaveJugador(id, actualizacion.getNaveId());

        UsuarioDTO respuesta= new UsuarioDTO();

        respuesta.setAvatar(jugador.getAvatar().getImagen());
        respuesta.setNombre(jugador.getNombre());
        respuesta.setRol(actualizacion.getRol());
        respuesta.setIdNave(actualizacion.getNaveId());

        return respuesta;
    }

}
