package com.proyecto.spaceinvaders.NaveEspacial.model;

import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class TipoNave {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique=true)
    private String tipo;
    @Column(nullable = false)
    private Float volBodega;
    @Column(nullable = false)
    private Float velocidad;
    private String foto;

    @OneToMany (mappedBy = "tipoNave")
    private ArrayList<Nave> Naves= new ArrayList<>();

    public TipoNave(String tipo, Float volBodega,  Float velocidad, String foto)
    {
        this.tipo=tipo;
        this.volBodega=volBodega;
        this.velocidad=velocidad;
        this.foto=foto;
    }


}
