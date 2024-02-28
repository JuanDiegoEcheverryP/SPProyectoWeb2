package com.proyecto.spaceinvaders.NaveEspacial.model;

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
public class Producto_bodega {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Integer cantidad;
    @Column(nullable = false)
    private Float volTotal;

    @ManyToOne
    private Nave nave;

    @ManyToOne
    private Producto producto;

    public Producto_bodega(Integer cantidad, Float volTotal)
    {
        this.cantidad=cantidad;
        this.volTotal=volTotal;
    }

    

}
