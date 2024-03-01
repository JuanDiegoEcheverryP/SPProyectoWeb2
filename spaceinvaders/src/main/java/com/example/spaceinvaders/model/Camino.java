package com.example.spaceinvaders.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"estrellaInicio_id", "estrellaFinal_id"})})
public class Camino {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nombre;

    Float distancia;

    @ManyToOne
    private Estrella  estrellaInicio;

    @ManyToOne
    private Estrella  estrellaFinal;
   
    public Camino(Estrella  estrellaInicio,Estrella  estrellaFinal, String nombre, Float distancia)
    {
        this.distancia=distancia;
        this.estrellaInicio=estrellaInicio;
        this.estrellaFinal=estrellaFinal;
        this.nombre=nombre;
    }
}