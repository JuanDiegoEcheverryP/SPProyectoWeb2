package com.example.spaceinvaders.model;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.ArrayList;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"coord_x", "coord_y", "coord_z"})})
public class Estrella {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique=true)
    private String nombre;
    //@Column(nullable = false)
    //private Boolean habitada;

    //la idea es que no hayan dos o mas estrellas en la misma posicion
    @Column(nullable = false)
    private Integer coord_x;
    @Column(nullable = false)
    private Integer coord_y;
    @Column(nullable = false)
    private Integer coord_z;
    
    @OneToMany (mappedBy = "estrellaFinal")
    private List<Camino> listaCaminosFinal = new ArrayList<>();

    @OneToMany (mappedBy = "estrellaInicio")
    private List<Camino> listaCaminosInicio = new ArrayList<>();

    @OneToMany (mappedBy = "estrella")
    private List<Planeta> listaPlanetas = new ArrayList<>();

    public Estrella(String nombre,Integer coord_x,Integer coord_y,Integer coord_z)
    {
        this.nombre=nombre;
        this.coord_x=coord_x;
        this.coord_y=coord_y;
        this.coord_z=coord_z;
    }
   
}