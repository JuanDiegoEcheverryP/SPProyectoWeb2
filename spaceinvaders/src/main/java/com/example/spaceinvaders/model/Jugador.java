package com.example.spaceinvaders.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Jugador {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique=true,nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String contrasena;

    @Column(columnDefinition = "VARCHAR(255) CHECK (rol IN ('capitan', 'piloto', 'comerciante'))",nullable = false)
    private String rol;

    @ManyToOne
    @JsonIgnore
    private Nave naveJuego;

    @ManyToOne
    private Avatar avatar;

    public Jugador(String nombre, String contrasena, String rol, Nave naveJuego, Avatar avatar)
    {
        this.nombre=nombre;
        this.contrasena=contrasena;
        this.rol=rol;
        this.naveJuego=naveJuego;
        this.avatar=avatar;
    }

}
