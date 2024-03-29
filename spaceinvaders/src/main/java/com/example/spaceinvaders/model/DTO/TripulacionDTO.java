package com.example.spaceinvaders.model.DTO;
import com.example.spaceinvaders.model.Nave;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TripulacionDTO {
    String capitan;
    Integer cantidadTripulantes;
    Nave nave;
}
