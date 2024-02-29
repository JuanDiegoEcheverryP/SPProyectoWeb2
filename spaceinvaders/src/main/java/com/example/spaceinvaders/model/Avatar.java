package com.example.spaceinvaders.model;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Avatar {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique=true,nullable = false)
    private String nombre;

    private String imagen;

    @OneToMany (mappedBy = "avatar")
    private List<Jugador> Jugadores= new ArrayList<>();

    public Avatar(String nombre, String url)
    {
        this.nombre=nombre;
        this.imagen=url;
    }
}