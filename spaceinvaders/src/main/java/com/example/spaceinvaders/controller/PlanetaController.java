package com.example.spaceinvaders.controller;
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
import com.example.spaceinvaders.exceptions.RepeatedCoordinateException;
import com.example.spaceinvaders.exceptions.RepeatedNameException;
import com.example.spaceinvaders.exceptions.UnableToDeletePlanetaException;
import com.example.spaceinvaders.model.Estrella;
import com.example.spaceinvaders.model.Planeta;
import com.example.spaceinvaders.model.Producto;
import com.example.spaceinvaders.services.EstrellaService;
import com.example.spaceinvaders.services.PlanetaService;
import com.example.spaceinvaders.services.StockPlanetaService;

@Controller
@RequestMapping("/api/planeta")
public class PlanetaController {
    
    Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private PlanetaService planetaService;

    @Autowired
    private StockPlanetaService stockPlanetaService;

    @Autowired
    private EstrellaService estrellaService;


    @GetMapping("/list")
    public String listarplanetas(Model model) {
        List<Planeta> planetas = planetaService.listaTodos();
        model.addAttribute("planeta", planetas);
        return "planeta_CRUD/planeta-list";
    }

    @GetMapping("/editar/{id}")
    public String editarplaneta(Model model, @PathVariable Long id) {
        Planeta planeta = planetaService.recuperarPlaneta(id);
        model.addAttribute("planeta", planeta);
        return "planeta_CRUD/planeta-edit";
    }

    @GetMapping("/ver/{idplaneta}")
    String verPersona(Model model, @PathVariable("idplaneta") Long id) {
        Planeta planeta = planetaService.recuperarPlaneta(id);
        model.addAttribute("planeta", planeta);
        return "planeta_CRUD/planeta-view";
    }

    @GetMapping("/search")
    public String listplaneta(@RequestParam(required = false) String searchText, Model model) {
        log.info("Solicitud GET recibida en /planeta/search");

        List<Planeta> planetas=new ArrayList<>();

        if (searchText == null || searchText.trim().equals("")) {
            log.info("No hay texto de búsqueda. Retornando todo");
            planetas= planetaService.listaTodos();
        } 
        else if (searchText.startsWith("*") && searchText.endsWith("*")) {
            
            log.info("Texto de búsqueda inicia y termina con un asterisco (*). Realizando búsqueda especial");

            String textoSinAsteriscos = searchText.substring(1, searchText.length() - 1);
            System.out.println(textoSinAsteriscos);
            planetas = planetaService.buscarPlanetasQueContengan(textoSinAsteriscos);


        } else if (searchText.startsWith("*")) {
            // El texto de búsqueda inicia con un asterisco (*)
            log.info("Texto de búsqueda inicia con un asterisco (*). Realizando búsqueda especial");

            String textoSinAsteriscoInicial = searchText.substring(1);
            System.out.println(textoSinAsteriscoInicial);
            planetas = planetaService.buscarPlanetasQueTerminenCon(textoSinAsteriscoInicial);

        } else if (searchText.endsWith("*")) {

            log.info("Texto de búsqueda termina con un asterisco (*). Realizando búsqueda especial");
           
            String textoSinAsteriscoFinal = searchText.substring(0, searchText.length() - 1);
            System.out.println(textoSinAsteriscoFinal);
            planetas = planetaService.buscarPlanetasQueEmpiecenCon(textoSinAsteriscoFinal);
        }
        else {
            log.info("Buscando personas cuyo apellido comienza con {}", searchText);
            planetas = planetaService.buscarNombre(searchText);
        }

        model.addAttribute("planetas", planetas);
        return "planeta_CRUD/planeta-search";
    }

    @PostMapping(value = "/guardar")
    public String guardarplaneta(@Valid Planeta planeta, BindingResult result, Model model) throws RepeatedCoordinateException, RepeatedNameException, NotNullException {
       
        String err2 = planetaService.planetaValidationNombre(planeta);
    
        if (result.hasErrors() || !err2.isEmpty()) {
            
            if (!err2.isEmpty()) {
                throw new RepeatedNameException(err2);
            }  
           
            return "planeta_CRUD/planeta-edit"; // Regresa a la vista para mostrar los errores
        }
        else if (planeta.getHabitado()==null || planeta.getNombre()==null ||planeta.getNombre()=="") {
            throw new NotNullException("todos los campos deben estar llenos");
        }

        if(planeta.getHabitado()==false)
        {
            //toca borrar todo lo de stock planeta porque esta deshabitado
            stockPlanetaService.deleteAllByPlaneta(planeta);
        }
    
        planetaService.guardarPlaneta(planeta);
        return "redirect:/planeta/menu";
    }
    
    @GetMapping("/borrar-form/{id}")
    public String borrarFormPlaneta(Model model, @PathVariable Long id)
    {
        Planeta planeta = planetaService.recuperarPlaneta(id);
        model.addAttribute("planeta", planeta);
        return "planeta_CRUD/planeta-delete";
    }
        
    @PostMapping("/borrar")
    public String borrarplaneta(@Valid Planeta planeta, BindingResult result, Model model) throws UnableToDeletePlanetaException
    { 
        planetaService.borrarPlaneta(planeta);
        return "redirect:/planeta/menu";
    }

    @PostMapping("/crear")
    public String crearplaneta(@Valid Planeta planeta, BindingResult result, Model model) throws RepeatedNameException, NotNullException
    {

        String err2 = planetaService.planetaValidationNombre(planeta);
    
        if (result.hasErrors() || !err2.isEmpty()) {
            
            if (!err2.isEmpty()) {
                throw new RepeatedNameException(err2);
            }   

            return "planeta_CRUD/planeta-crear"; // Regresa a la vista para mostrar los errores
        }
        else if (planeta.getHabitado()==null || planeta.getNombre()==null || planeta.getNombre()=="") {
            throw new NotNullException("todos los campos deben estar llenos");
        }
        
        planetaService.crearPlaneta(planeta);

        return "redirect:/planeta/menu";
    }

    @RequestMapping("/creador")
    public String creador(Model model) {
        List<Estrella> estrellasTodas=estrellaService.listaEstrellas();
        model.addAttribute("planeta", new Planeta());
        model.addAttribute("estrella", estrellasTodas.subList(0,1000));
        return "planeta_CRUD/planeta-create";
    }

    @RequestMapping("/menu")
    public String menu() {
        return "planeta_CRUD/planeta-menu";
    }

    @RequestMapping("/researcher")
    public String buscador() {
        return "planeta_CRUD/planeta-search";
    }

    /*@RequestMapping("/listar")
    public String listar() {
        return "redirect:/planeta/list";
    }*/
}
