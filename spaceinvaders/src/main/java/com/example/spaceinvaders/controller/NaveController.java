package com.example.spaceinvaders.controller;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.spaceinvaders.model.Camino;
import com.example.spaceinvaders.model.Estrella;
import com.example.spaceinvaders.model.Nave;
import com.example.spaceinvaders.model.DTO.NaveDTO;
import com.example.spaceinvaders.model.DTO.TripulacionDTO;
import com.example.spaceinvaders.services.NaveService;


@RestController
@RequestMapping("/api/nave")
@CrossOrigin(origins = "http://localhost:4200/")
public class NaveController {
    @Autowired
    private NaveService naveService;

    @GetMapping("/list")
    public List<Nave> listarNaves() {
        return naveService.listaNaves();
    } 

    @GetMapping("/ver/{id}")
    public ResponseEntity<?> obtenerInfoNave( @PathVariable Long id) { 
        try
        {
            Nave naveObtenida=naveService.recuperarNave(id);
            NaveDTO respuesta=new NaveDTO();
            respuesta.setNombre(naveObtenida.getNombre());
            respuesta.setTipoNave(naveObtenida.getTipoNave().getNombre());
            respuesta.setCreditos(naveObtenida.getCredito());
            respuesta.setTiempoRestante(naveObtenida.getTiempo());
            respuesta.setCapacidadMaxima(naveObtenida.getTipoNave().getVolBodega());
            respuesta.setFoto(naveObtenida.getTipoNave().getFoto());
            respuesta.setVelocidad(naveObtenida.getTipoNave().getVelocidad());

            //obtener la localizacion
            //fijar estrella
            respuesta.setIdEstrella(naveObtenida.getLocalizacion().getId());
            respuesta.setNombreEstrella(naveObtenida.getLocalizacion().getNombre());

            //fijar planeta 
            if (naveObtenida.getLocalizacionPlaneta() != null) {
                respuesta.setIdPlaneta(naveObtenida.getLocalizacionPlaneta().getId());
                respuesta.setNombrePlaneta(naveObtenida.getLocalizacionPlaneta().getNombre());
            } else {
                respuesta.setIdPlaneta(null);
                respuesta.setNombrePlaneta(null);
            }

            if(naveService.obtenerVolumenTotal(id)!=null)
            {
                respuesta.setCapadidadUtilizada(naveService.obtenerVolumenTotal(id));
            }
            else
            {
                respuesta.setCapadidadUtilizada(0f);
            }
            //fijar volumen que se ha utilizado

            return ResponseEntity.ok().body(respuesta);
        }
        catch (NoSuchElementException e)
        {
            String errorMessage = "No existe una nave con ese id";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }

    @GetMapping("/obtenerPlaneta/{id}")
    public ResponseEntity<?> obtenerPlanetaPorNaveId(@PathVariable Long id) {
        try{
            return ResponseEntity.ok().body(naveService.obtenerPlanetaPorNaveId(id));
        }
        catch (NoSuchElementException e)
        {
            String errorMessage = "No existe un planeta para esa nave con dicho id";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }  
    }

    
    @GetMapping("/obtenerEstrella/{id}")
    public Estrella obtenerEstrellaPorNaveId(@PathVariable Long id) {
        return naveService.obtenerEstrellaPorNaveId(id);
    }

    @GetMapping("/tripulaciones")
    public List<TripulacionDTO> obtenerInfotripulaciones() {
        return naveService.obtenerInfotripulaciones();
    }

    @Secured({ "capitan", "piloto" })
    @PutMapping("/actualizar/{idNave}/{idEstrella}")
    public List<Nave> actualizarLocalizacionEstrella(@PathVariable Long idNave,@PathVariable Long idEstrella) {
        naveService.actualizarLocalizacionEstrella(idNave,idEstrella);
        return naveService.listaNaves();
    }

    @Secured({ "capitan", "piloto" })
    @PutMapping("/actualizarBien/{idNave}/{idEstrella}/{idPlaneta}")
    public boolean actualizarNave(@PathVariable Long idNave,@PathVariable Long idEstrella,@PathVariable Long idPlaneta) {
        Nave naveObtenida=naveService.recuperarNave(idNave);
        Camino camin = naveService.obtenerCaminoDosEstrellas(naveObtenida.getLocalizacion().getId(),idEstrella);
        if ((long)naveObtenida.getLocalizacion().getId() == idEstrella) {
            naveService.actualizarLocalizacionEstrellaConPlaneta(idNave,idPlaneta);
            return true;
        }
        else if (camin.getDistancia() == null || naveObtenida.getTiempo() - camin.getDistancia() < 0) {
            System.out.println(camin.getDistancia());
            return false;
        }
        else {
            naveService.actualizarLocalizacionEstrella(idNave,idEstrella);
            naveService.actualizarLocalizacionEstrellaConPlaneta(idNave,idPlaneta);
            naveService.actualizarCantidad(idNave, camin.getDistancia());
            return true;
        }
        
    }

    @PostMapping("registro/{idCapitan}/{nombreNave}/{tipoNave}")
    public ResponseEntity<?> registrarConNave(@PathVariable Long idCapitan,@PathVariable String nombreNave,@PathVariable Long tipoNave) {
        return ResponseEntity.ok(naveService.registrarConNave(idCapitan,nombreNave,tipoNave));
        
    } 

    @GetMapping("exist/{nombreNave}")
    public boolean naveExist(@PathVariable String nombreNave) {
        return naveService.naveExist(nombreNave);
    }
}
