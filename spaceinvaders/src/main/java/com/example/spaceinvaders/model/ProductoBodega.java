package com.example.spaceinvaders.model;

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
    private Nave nave;

    @ManyToOne
    private Producto producto;

    public ProductoBodega(Integer cantidad, Float vol)
    {
        this.cantidad=cantidad;
        this.vol=vol;
    }


    

}
