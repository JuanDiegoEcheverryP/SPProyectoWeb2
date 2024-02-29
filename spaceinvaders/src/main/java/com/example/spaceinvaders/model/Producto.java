package com.example.spaceinvaders.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.OneToMany;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique=true)
    private String nombre;
    @Column(nullable = false)
    private float volumen;
    private String imagen;

    @OneToMany (mappedBy ="producto")
    private List<Stock_planeta> listaStockPlanetas = new ArrayList<>();

    public void anadirStockPlaneta(Stock_planeta nuevo)
    {
        listaStockPlanetas.add(nuevo);
    }


    @OneToMany (mappedBy = "producto")
    private List<Producto_bodega> listaPRoductoBodega = new ArrayList<>();

    public void anadirProductoBodega(Producto_bodega nuevo)
    {
        listaPRoductoBodega.add(nuevo);
    }

    public Producto(String nombre,String imagen,float volumen)
    {
        this.nombre=nombre;
        this.imagen=imagen;
        this.volumen=volumen;
    }

}
