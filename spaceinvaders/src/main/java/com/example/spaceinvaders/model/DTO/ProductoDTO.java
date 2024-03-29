package com.example.spaceinvaders.model.DTO;

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
    private Float precioDemanda;
    private Float precioOferta;
    Integer max;

    public ProductoDTO(Long id,String nombre,String imagen,Float volumen,Long FactorDemanda,Long FactorOferta,Integer stock)
    {
        this.id=id;
        this.nombre=nombre;
        this.imagen=imagen;
        this.volumen=volumen;
        this.precioDemanda=(float) (FactorDemanda/(1+stock));
        this.precioOferta=(float) (FactorOferta/(1+stock));
    }

    public ProductoDTO(Long id,String nombre,String imagen,Float volumen,Long FactorDemanda,Long FactorOferta,Integer stock, Integer max)
    {
        this.id=id;
        this.nombre=nombre;
        this.imagen=imagen;
        this.volumen=volumen;
        this.precioDemanda=(float) (FactorDemanda/(1+stock));
        this.precioOferta=(float) (FactorOferta/(1+stock));
        this.max=max;
    }

}
