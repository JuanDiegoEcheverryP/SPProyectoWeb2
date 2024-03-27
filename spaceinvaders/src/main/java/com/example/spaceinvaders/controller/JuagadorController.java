package com.example.spaceinvaders.controller;

import java.util.List; // Make sure to import java.util.Lis
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spaceinvaders.model.Jugador;
import com.example.spaceinvaders.model.DTO.JugadorLogIn;
import com.example.spaceinvaders.model.DTO.UsuarioDTO;
import com.example.spaceinvaders.services.AvatarService;
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
    public UsuarioDTO iniciarSesion(@RequestBody JugadorLogIn jugador) {
       
        
        List<Jugador> encontrado=jugadorService.obtenerJugadorXUsuarioXContrasena(jugador);
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

        }

        return usuario;
    }

}
