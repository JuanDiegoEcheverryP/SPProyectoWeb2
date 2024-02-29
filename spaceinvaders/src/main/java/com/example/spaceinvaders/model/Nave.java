package com.example.spaceinvaders.model;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Nave {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(unique=true)
    private String nombre;
    private Float credito;
    private Float tiempo;

    @ManyToOne
    private Planeta localizacion; // Correcting the mapping to Planeta entity

    @OneToMany(mappedBy = "nave")
    private List<Producto_bodega> productosDeBodega = new ArrayList<>();
    
    public void anadirProductoBodega(Producto_bodega nuevo)
    {
        productosDeBodega.add(nuevo);
    }

    @ManyToOne
    private TipoNave tipoNave;

    @OneToMany(mappedBy = "naveJuego")
    private List<Jugador> jugadores = new ArrayList<>();

    public Nave(String nombre, Float credito, Float tiempo,Planeta localizacion,TipoNave tipoNave )
    {
        this.nombre=nombre;
        this.tiempo=tiempo;
        this.credito=credito;
        this.localizacion=localizacion;
        this.tipoNave=tipoNave;
    }
    
}
