package com.example.spaceinvaders.model.DTO;
import com.example.spaceinvaders.model.Enum.Rol;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatchRolNave {
    Rol rol;
    Long naveId;

    public PatchRolNave(Rol rol, Long naveId)
    {
        this.naveId=naveId;

        this.rol=rol;
    }
}
