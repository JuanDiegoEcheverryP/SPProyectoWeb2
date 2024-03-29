package com.example.spaceinvaders.model.DTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioDTO {

    Long id;
    String nombre;
    String rol;
    String avatar;
    Long idNave;
}
