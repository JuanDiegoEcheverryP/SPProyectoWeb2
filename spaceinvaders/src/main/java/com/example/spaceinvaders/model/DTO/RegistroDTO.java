package com.example.spaceinvaders.model.DTO;

import com.example.spaceinvaders.model.Avatar;
import com.example.spaceinvaders.model.Nave;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegistroDTO {

    private Long id;
    private String nombre;
    private String contrasena;
    private String contrasena2;
    private String rol;
    private Nave naveJuego;
    private Avatar avatar;

    public RegistroDTO(String nombre, String contrasena, String rol, Nave naveJuego, Avatar avatar,String contrasena2)
    {
        this.nombre=nombre;
        this.contrasena=contrasena;
        this.rol=rol;
        this.naveJuego=naveJuego;
        this.avatar=avatar;
        this.contrasena2=contrasena2;
    }

}
