package com.example.spaceinvaders.model;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.ArrayList;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.CascadeType;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"coord_x", "coord_y", "coord_z"})})
public class Estrella {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique=true)
    private String nombre;
    //@Column(nullable = false)
    //private Boolean habitada;

    //la idea es que no hayan dos o mas estrellas en la misma posicion
    @Column(nullable = false)
    private Float coord_x;
    @Column(nullable = false)
    private Float coord_y;
    @Column(nullable = false)
    private Float coord_z;
    
    @OneToMany (mappedBy = "estrellaFinal",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Camino> listaCaminosFinal = new ArrayList<>();

    @OneToMany (mappedBy = "estrellaInicio",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    private List<Camino> listaCaminosInicio = new ArrayList<>();

    @OneToMany (mappedBy = "estrella",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Planeta> listaPlanetas = new ArrayList<>();

    @OneToMany (mappedBy = "localizacion")
    private List<Nave> listaNaves = new ArrayList<>();

    public Estrella(String nombre,Float coord_x,Float coord_y,Float coord_z)
    {
        this.nombre=nombre;
        this.coord_x=coord_x;
        this.coord_y=coord_y;
        this.coord_z=coord_z;
    }
   
}