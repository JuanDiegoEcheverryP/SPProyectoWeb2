package com.example.spaceinvaders.controller.olds;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.spaceinvaders.exceptions.NotNullException;
import com.example.spaceinvaders.exceptions.OutOfLimitsException;
import com.example.spaceinvaders.exceptions.RepeatedCoordinateException;
import com.example.spaceinvaders.exceptions.RepeatedNameException;
import com.example.spaceinvaders.exceptions.UnableToDeletePlanetaException;
import com.example.spaceinvaders.model.Estrella;
import com.example.spaceinvaders.services.EstrellaService;

@Controller
@RequestMapping("/estrella")
public class EstrellaControllerOld {
    
    Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private EstrellaService estrellaService;

    
    @GetMapping("/list")
    public String listarEstrellas(Model model) {
        List<Estrella> estrellas = estrellaService.listaEstrellas();
        model.addAttribute("estrella", estrellas);
        return "Estrella_CRUD/estrella-list";
    }

    @GetMapping("/editar/{id}")
    public String editarEstrella(Model model, @PathVariable Long id) {
        Estrella estrella = estrellaService.recuperarEstrella(id);
        model.addAttribute("estrella", estrella);
        return "Estrella_CRUD/estrella-edit";
    }

    @GetMapping("/ver/{idEstrella}")
    String verEstrella(Model model, @PathVariable("idEstrella") Long id) {
        Estrella estrella = estrellaService.recuperarEstrella(id);
        model.addAttribute("estrella", estrella);
        return "Estrella_CRUD/estrella-view";
    }

    @GetMapping("/search")
    public String listEstrella(@RequestParam(required = false) String searchText, Model model) {
        log.info("Solicitud GET recibida en /estrella/search");

        List<Estrella> estrellas=new ArrayList<>();

        if (searchText == null || searchText.trim().equals("")) {
            log.info("No hay texto de búsqueda. Retornando todo");
            estrellas= estrellaService.listaEstrellas();
        } 
        else if (searchText.startsWith("*") && searchText.endsWith("*")) {
            
            log.info("Texto de búsqueda inicia y termina con un asterisco (*). Realizando búsqueda especial");

            String textoSinAsteriscos = searchText.substring(1, searchText.length() - 1);
            System.out.println(textoSinAsteriscos);
            estrellas = estrellaService.buscarEstrellasQueContengan(textoSinAsteriscos);


        } else if (searchText.startsWith("*")) {
            // El texto de búsqueda inicia con un asterisco (*)
            log.info("Texto de búsqueda inicia con un asterisco (*). Realizando búsqueda especial");

            String textoSinAsteriscoInicial = searchText.substring(1);
            System.out.println(textoSinAsteriscoInicial);
            estrellas = estrellaService.buscarEstrellasQueTerminenCon(textoSinAsteriscoInicial);

        } else if (searchText.endsWith("*")) {

            log.info("Texto de búsqueda termina con un asterisco (*). Realizando búsqueda especial");
           
            String textoSinAsteriscoFinal = searchText.substring(0, searchText.length() - 1);
            System.out.println(textoSinAsteriscoFinal);
            estrellas = estrellaService.buscarEstrellasQueEmpiecenCon(textoSinAsteriscoFinal);
        }
        else {
            log.info("Buscando personas cuyo apellido comienza con {}", searchText);
            estrellas = estrellaService.buscarNombre(searchText);
        }

        model.addAttribute("estrellas", estrellas);
        return "Estrella_CRUD/estrella-search";
    }

    @PostMapping(value = "/guardar")
    public String guardarEstrella(@Valid Estrella estrella, BindingResult result, Model model) throws RepeatedCoordinateException, RepeatedNameException, NotNullException, OutOfLimitsException {
        String err = estrellaService.estrellaValidationCoord(estrella);
        String err2 = estrellaService.estrellaValidationNombre(estrella);
    
        if (result.hasErrors() || !err.isEmpty() || !err2.isEmpty()) {
            
            if (!err2.isEmpty()) {
                throw new RepeatedNameException(err2);
            }  
           
            if (!err.isEmpty()) {
                throw new RepeatedCoordinateException(err);
            }   

            return "Estrella_CRUD/estrella-edit"; // Regresa a la vista para mostrar los errores
        }
        else if (estrella.getCoord_x()==null || estrella.getCoord_y()==null || estrella.getCoord_z()==null || estrella.getNombre()==null)
        {
            throw new NotNullException("todos los campos deben estar llenos");
        }
        else if (estrella.getCoord_x()>300 || estrella.getCoord_x()<0 || estrella.getCoord_y()>300 || estrella.getCoord_y()<0 || estrella.getCoord_z()>300 || estrella.getCoord_z()<0)
        {
            throw new OutOfLimitsException("Las coordenadas solo pueden estar entre 0 y 300");
        }
    
        estrellaService.guardarEstrella(estrella);
        return "redirect:/estrella/menu";
    }
    
    @GetMapping("/borrar-form/{id}")
    public String borrarFormEstrella(Model model, @PathVariable Long id)
    {
        Estrella estrella = estrellaService.recuperarEstrella(id);
        model.addAttribute("estrella", estrella);
        return "Estrella_CRUD/estrella-delete";
    }

    @PostMapping("/borrar")
    public String borrarEstrella(@Valid Estrella estrella, BindingResult result, Model model) throws UnableToDeletePlanetaException
    {
        String err= estrellaService.estrellaValidationNaves(estrella);

        if(!err.isEmpty())
        {
            System.out.println(err);
            throw new UnableToDeletePlanetaException(err);
        }
        
        estrellaService.borrarEstrella(estrella);

        return "redirect:/estrella/menu";
    }

    @PostMapping("/crear")
    public String crearEstrella(@Valid Estrella estrella, BindingResult result, Model model) throws RepeatedNameException, RepeatedCoordinateException, NotNullException, OutOfLimitsException
    {
       //estrellaService
       String err = estrellaService.estrellaValidationCoord(estrella);
        String err2 = estrellaService.estrellaValidationNombre(estrella);
    
        if (result.hasErrors() || !err.isEmpty() || !err2.isEmpty()) {
            
            if (!err2.isEmpty()) {
                throw new RepeatedNameException(err2);
            }  
           
            if (!err.isEmpty()) {
                throw new RepeatedCoordinateException(err);
            }   

            return "Estrella_CRUD/estrella-crear"; // Regresa a la vista para mostrar los errores
        }
        else if (estrella.getCoord_x()==null || estrella.getCoord_y()==null || estrella.getCoord_z()==null || estrella.getNombre()==null)
        {
            throw new NotNullException("todos los campos deben estar llenos");
        }
        else if (estrella.getCoord_x()>300 || estrella.getCoord_x()<0 || estrella.getCoord_y()>300 || estrella.getCoord_y()<0 || estrella.getCoord_z()>300 || estrella.getCoord_z()<0)
        {
            throw new OutOfLimitsException("Las coordenadas solo pueden estar entre 0 y 300");
        }

        
        estrellaService.crearEstrella(estrella);

        return "redirect:/estrella/menu";
    }

    @RequestMapping("/creador")
    public String creador(Model model) {
        model.addAttribute("estrella", new Estrella());
        return "Estrella_CRUD/estrella-create";
    }

    @RequestMapping("/menu")
    public String menu() {
        return "Estrella_CRUD/estrella-menu";
    }

    @RequestMapping("/researcher")
    public String buscador() {
        return "Estrella_CRUD/estrella-search";
    }

   /* @RequestMapping("/listar")
    public String listar() {
        return "redirect:/estrella/list";
    }*/ 
    
}
