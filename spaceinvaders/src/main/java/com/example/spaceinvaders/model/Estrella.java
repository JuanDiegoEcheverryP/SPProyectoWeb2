package com.proyecto.spaceinvaders.NaveEspacial.model;

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
    private Integer coord_x;
    @Column(nullable = false)
    private Integer coord_y;
    @Column(nullable = false)
    private Integer coord_z;
    
    @OneToMany (mappedBy = "estrellaFinal")
    private ArrayList<Camino> listaCaminosFinal = new ArrayList<>();

    @OneToMany (mappedBy = "estrellaInicio")
    private ArrayList<Camino> listaCaminosInicio = new ArrayList<>();

    @OneToMany (mappedBy = "estrella")
    ArrayList<Planeta> listaPlanetas = new ArrayList<>();
   
}