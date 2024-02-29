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
import jakarta.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Planeta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique=true)
    private String nombre;

    private Boolean habitado;
    
    private String imagen; //Este seria una url a una imagen

    @OneToMany (mappedBy = "planeta")
    private List<Stock_planeta> listaStock = new ArrayList<>();
    
    public void anadirStockPlaneta(Stock_planeta nuevo)
    {
        listaStock .add(nuevo);
    }

    @OneToMany (mappedBy = "localizacion")
    private List<Nave> listaNaves = new ArrayList<>();

    @ManyToOne
    private Estrella estrella;

    public Planeta(String nombre,Boolean habitado,String imagen,Estrella estrella)
    {
        this.nombre=nombre;
        this.habitado=habitado;
        this.imagen=imagen;
        this.estrella=estrella;
    }
}


//Flujo principal y flujos alternativos en vez de lo que estabamos haciendo de C.U