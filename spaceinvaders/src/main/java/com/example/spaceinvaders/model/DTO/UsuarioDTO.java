package com.example.spaceinvaders.model.DTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioDTO {

    private Long id;
    private String nombre;
    private String rol;
    private String avatar;
    private Long idNave;

    public UsuarioDTO(Long id, String nombre, String rol, String avatar, Long idNave) {
        this.id = id;
        this.nombre = nombre;
        this.rol = rol;
        this.avatar = avatar;
        this.idNave = idNave;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        result = prime * result + ((rol == null) ? 0 : rol.hashCode());
        result = prime * result + ((avatar == null) ? 0 : avatar.hashCode());
        result = prime * result + ((idNave == null) ? 0 : idNave.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UsuarioDTO other = (UsuarioDTO) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (nombre == null) {
            if (other.nombre != null)
                return false;
        } else if (!nombre.equals(other.nombre))
            return false;
        if (rol == null) {
            if (other.rol != null)
                return false;
        } else if (!rol.equals(other.rol))
            return false;
        if (avatar == null) {
            if (other.avatar != null)
                return false;
        } else if (!avatar.equals(other.avatar))
            return false;
        if (idNave == null) {
            if (other.idNave != null)
                return false;
        } else if (!idNave.equals(other.idNave))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "UsuarioDTO [id=" + id + ", nombre=" + nombre + ", rol=" + rol + ", avatar=" + avatar + ", idNave="
                + idNave + "]";
    }
}

