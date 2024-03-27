package com.example.spaceinvaders.model.DTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioDTO {

    String nombre;
    String rol;
    String avatar;
    Long idNave;
}
