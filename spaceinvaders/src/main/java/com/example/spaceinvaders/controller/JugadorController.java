package com.example.spaceinvaders.controller;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spaceinvaders.model.Jugador;
import com.example.spaceinvaders.model.Nave;
import com.example.spaceinvaders.model.DTO.JugadorLogInDTO;
import com.example.spaceinvaders.model.DTO.PatchRolNave;
import com.example.spaceinvaders.model.DTO.RegistroDTO;
import com.example.spaceinvaders.model.DTO.UsuarioDTO;
import com.example.spaceinvaders.services.JugadorService;
import com.example.spaceinvaders.services.JwtService;

@RestController
@RequestMapping("/api/jugador")
@CrossOrigin(origins = "http://localhost:4200/")
public class JugadorController {

    @Autowired
    private JugadorService jugadorService;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;
    
    //buscar jugador para iniciar sesion
    //este retorna el jugador, exceptuando su contrasena
    @PostMapping("login")
    public ResponseEntity<?> iniciarSesion(@RequestBody JugadorLogInDTO jugador) {
       
        UsuarioDTO encontrado=jugadorService.obtenerJugadorXUsuarioXContrasena(jugador.getNombre(),jugador.getContrasena());
       
        if(encontrado!=null)
        {
            return ResponseEntity.ok(encontrado);
        }
        else
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Contraseña o nombre de usuario incorrecto");
        }

    }

    @GetMapping("/nave/{id}")
    public Nave obtenerNavePorJugadorId(@PathVariable Long id) {
        return jugadorService.obtenerNavePorJugadorId(id);
    }

    @PostMapping("registro")
    public ResponseEntity<?> registrarse(@RequestBody RegistroDTO registro) {
        UsuarioDTO usuario = new UsuarioDTO();
        Jugador jugador=new Jugador();

        if(registro.getContrasena().equals( registro.getContrasena2()))
        {
            jugador.setNombre(registro.getNombre());
            jugador.setAvatar(registro.getAvatar());
            jugador.setContrasena(passwordEncoder.encode(registro.getContrasena()));
            
            try {
                usuario.setId(jugadorService.crearJugador(jugador).getId());
                usuario.setNombre(jugador.getNombre());
                usuario.setIdNave(null);
                usuario.setAvatar(jugador.getAvatar().getImagen());
                String jwt = jwtService.generateToken(jugador);
                usuario.setToken(jwt);
                return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
            } catch (DataIntegrityViolationException e) {
                // El nombre de jugador ya existe, manejar el error aquí
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                    .body("El nombre de usuario ya está en uso");
            }
        }
        else
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("Las contraseñas no son iguales");
        }
         
    } 

    @PatchMapping("{id}/rol/nave")
    public ResponseEntity<?> modificarRolYNave(@PathVariable Long id, @RequestBody PatchRolNave actualizacion) {
        Jugador jugador=jugadorService.recuperarJugador(id);

        jugadorService.actualizarRolJugador(id,actualizacion.getRol());

        jugadorService.actualizarNaveJugador(id, actualizacion.getNaveId());

        UsuarioDTO respuesta= new UsuarioDTO();
        respuesta.setId(id);
        respuesta.setAvatar(jugador.getAvatar().getImagen());
        respuesta.setNombre(jugador.getNombre());
        respuesta.setRol(actualizacion.getRol());
        respuesta.setIdNave(actualizacion.getNaveId());

        return ResponseEntity.ok().body(respuesta);
    }

    @GetMapping("/{idJugador}")
    public ResponseEntity<?> verJugador(@PathVariable Long idJugador) {
        try
        {
            Jugador jugador = jugadorService.recuperarJugador(idJugador);
            UsuarioDTO enviar=new UsuarioDTO();
            enviar.setId(jugador.getId());
            enviar.setNombre(jugador.getNombre());
            enviar.setRol(jugador.getRol());
            enviar.setAvatar(jugador.getAvatar().getImagen());
            enviar.setIdNave(jugador.getNaveJuego().getId());
            return ResponseEntity.ok().body(enviar);
        } 
        catch (NoSuchElementException e)
        {
            String errorMessage = "No existe un jugador con ese id";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
        
    }

}
