package com.example.spaceinvaders.model.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NaveDTO {
    
    String nombre;
    Long idEstrella;
    String nombreEstrella;
    Long idPlaneta;
    String nombrePlaneta;
    String tipoNave;
    Float creditos;
    Float tiempoRestante;
    Float capacidadMaxima;
    Float capadidadUtilizada;
    String foto;
    Float velocidad;

    public NaveDTO(String nombre, Long idEstrella, String nombreEstrella, Long idPlaneta, String nombrePlaneta, String tipoNave, Float creditos, Float tiempoRestante, Float capacidadMaxima, Float capadidadUtilizada, String foto, Float velocidad) {
        this.nombre = nombre;
        this.idEstrella = idEstrella;
        this.nombreEstrella = nombreEstrella;
        this.idPlaneta = idPlaneta;
        this.nombrePlaneta = nombrePlaneta;
        this.tipoNave = tipoNave;
        this.creditos = creditos;
        this.tiempoRestante = tiempoRestante;
        this.capacidadMaxima = capacidadMaxima;
        this.capadidadUtilizada = capadidadUtilizada;
        this.foto = foto;
        this.velocidad = velocidad;
    }

}
