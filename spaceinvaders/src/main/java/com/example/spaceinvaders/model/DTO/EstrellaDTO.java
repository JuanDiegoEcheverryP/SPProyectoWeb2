package com.example.spaceinvaders.model.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EstrellaDTO {
    private Long id;
    private String nombre;
    private Float distancia;
    private Boolean habitado;

    public EstrellaDTO(Long id,String nombre, Float distancia,Boolean habitado) {
        this.id = id;
        this.nombre = nombre;
        this.distancia = distancia;
        this.habitado = habitado;
    }
}
