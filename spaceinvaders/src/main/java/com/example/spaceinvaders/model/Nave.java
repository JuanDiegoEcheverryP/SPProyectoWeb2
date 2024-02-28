package com.proyecto.spaceinvaders.NaveEspacial.model;

import java.util.ArrayList;
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
    private ArrayList<Producto_bodega> productosDeBodega = new ArrayList<>();

        
    public void anadirProductoBodega(Producto_bodega nuevo)
    {
        productosDeBodega.add(nuevo);
    }


    @ManyToOne
    private TipoNave tipoNave;

    @OneToMany(mappedBy = "naveJuego")
    private ArrayList<Jugador> jugadores = new ArrayList<>();
    
}
