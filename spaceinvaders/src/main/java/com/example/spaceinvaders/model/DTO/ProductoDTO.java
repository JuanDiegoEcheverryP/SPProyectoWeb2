package com.example.spaceinvaders.model.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductoDTO {

    private Long id;

    private String nombre;

    private Float volumen;

    private String imagen;

    private Float precio;

    public ProductoDTO(String nombre,String imagen,float volumen,Long FactorDemanda,Long FactorOferta,Integer stock)
    {
        this.nombre=nombre;
        this.imagen=imagen;
        this.volumen=volumen;
        this.precio=(float) (FactorDemanda/(1+stock));
    }

}
