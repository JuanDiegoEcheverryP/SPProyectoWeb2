package com.example.spaceinvaders.model.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class productoBodegaDTO {
    
    private Long id;
    private String nombre;
    private int cantidad;
    private Float espacio;

    public productoBodegaDTO(Long id,String nombre,int cantidad,Float espacio)
    {
        this.id=id;
        this.nombre=nombre;
        this.cantidad = cantidad;
        this.espacio = espacio;
    }
}

