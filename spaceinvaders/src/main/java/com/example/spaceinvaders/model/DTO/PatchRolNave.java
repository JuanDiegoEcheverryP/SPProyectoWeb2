package com.example.spaceinvaders.model.DTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatchRolNave {
    String rol;
    Long naveId;

    public PatchRolNave(String rol, Long naveId)
    {
        this.naveId=naveId;

        this.rol=rol;
    }
}
