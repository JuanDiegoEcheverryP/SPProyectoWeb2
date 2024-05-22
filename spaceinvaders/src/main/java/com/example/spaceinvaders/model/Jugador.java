package com.example.spaceinvaders.model;

import com.example.spaceinvaders.model.Enum.Rol;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.jsonwebtoken.lang.Collections;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Jugador implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique=true,nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String contrasena;

    @Column(columnDefinition = "VARCHAR(255) CHECK (rol IN ('capitan', 'piloto', 'comerciante'))")
    @Enumerated(EnumType.STRING) 
    private Rol rol;

    @ManyToOne
    @JsonIgnore
    private Nave naveJuego;

    @ManyToOne
    private Avatar avatar;

    public Jugador(String nombre, String contrasena, Rol rol, Nave naveJuego, Avatar avatar)
    {
        this.nombre=nombre;
        this.contrasena=contrasena;
        this.rol=rol;
        this.naveJuego=naveJuego;
        this.avatar=avatar;
    }


    public String getPassword() {
        return contrasena;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.rol == null) {
            // Si el rol es nulo, devolver una colección vacía o cualquier otra lógica que desees
            return Collections.emptyList();
        }

        return List.of(new SimpleGrantedAuthority(rol.name()));
    }
    
    @Override
    public String getUsername() {
        // email in our case
        return nombre;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
