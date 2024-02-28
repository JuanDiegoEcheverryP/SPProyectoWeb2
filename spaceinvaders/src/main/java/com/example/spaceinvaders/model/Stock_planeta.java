package com.proyecto.spaceinvaders.NaveEspacial.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Stock_planeta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long FactorDemanda;
    private Long FactorOferta;
    private Integer stock;
    //private String imagen; //Este seria una url a una imagen

    //FK
    @ManyToOne
    private Planeta planeta;

    @ManyToOne
    private Producto producto;

    public Stock_planeta(Long FactorDemanda,Long FactorOferta,Integer stock)
    {
        this.FactorDemanda=FactorDemanda;
        this.FactorOferta=FactorOferta;
        this.stock=stock;
    }
}