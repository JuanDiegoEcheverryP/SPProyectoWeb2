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
public class ProductoBodega {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Integer cantidad;
    @Column(nullable = false)
    private Float vol;

    @ManyToOne
    @JsonIgnore
    private Nave nave;

    @ManyToOne
    @JsonIgnore
    private Producto producto;

    public ProductoBodega(Integer cantidad, Float vol)
    {
        this.cantidad=cantidad;
        this.vol=vol;
    }

    public ProductoBodega(Integer cantidad, Float vol, Producto producto, Nave nave)
    {
        this.cantidad=cantidad;
        this.vol=vol;
        this.producto=producto;
        this.nave=nave;
    }


    

}
