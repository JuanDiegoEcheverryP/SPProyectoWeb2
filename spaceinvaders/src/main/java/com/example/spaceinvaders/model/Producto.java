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
import jakarta.persistence.CascadeType;

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
    private Float volumen;
    private String imagen;

    @OneToMany (mappedBy ="producto",cascade = CascadeType.ALL)
    private List<Stock_planeta> listaStockPlanetas = new ArrayList<>();

    public void anadirStockPlaneta(Stock_planeta nuevo)
    {
        listaStockPlanetas.add(nuevo);
    }


    @OneToMany (mappedBy = "producto")
    private List<ProductoBodega> listaPRoductoBodega = new ArrayList<>();

    public void anadirProductoBodega(ProductoBodega nuevo)
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
