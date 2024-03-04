package com.example.spaceinvaders.controller;

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
import com.example.spaceinvaders.model.TipoNave;
import com.example.spaceinvaders.services.TipoNaveService;

@Controller
@RequestMapping("/tipoNave")
public class TipoNaveController {

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private TipoNaveService tipoNaveService;

    @GetMapping("/list")
    public String listarPersonas(Model model) {
        List<TipoNave> naves = tipoNaveService.listarTipoNaves();
        model.addAttribute("tipoNave", naves);
        return "TipoNave_CRUD/tipoNave-list";
    }

    @GetMapping("/editar/{id}")
    public String editarTipoNave(Model model, @PathVariable Long id) {
        TipoNave tipoNave = tipoNaveService.recuperarTipoNave(id);
        model.addAttribute("tipoNave", tipoNave);
        return "TipoNave_CRUD/tipoNave-edit";
    }

    @GetMapping("/ver/{id}")
    String verTipoNave(Model model, @PathVariable Long id) {
        TipoNave tipoNave = tipoNaveService.recuperarTipoNave(id);
        model.addAttribute("tipoNave", tipoNave);
        return "TipoNave_CRUD/tipoNave-view";
    }

    @GetMapping("/search")
    public String listTipoNave(@RequestParam(required = false) String searchText, Model model) {
        List<TipoNave> naves = tipoNaveService.listarTipoNaves();

        log.info("Solicitud GET recibida en /estrella/search");

        if (searchText == null || searchText.trim().equals("")) {
            log.info("No hay texto de búsqueda. Retornando todo");
            naves= tipoNaveService.listarTipoNaves();
        }
        else if (searchText.startsWith("*") && searchText.endsWith("*")) {
            
            log.info("Texto de búsqueda inicia y termina con un asterisco (*). Realizando búsqueda especial");

            String textoSinAsteriscos = searchText.substring(1, searchText.length() - 1);
            System.out.println(textoSinAsteriscos);
            naves = tipoNaveService.buscarTipoNavesQueContengan(textoSinAsteriscos);


        } else if (searchText.startsWith("*")) {
            // El texto de búsqueda inicia con un asterisco (*)
            log.info("Texto de búsqueda inicia con un asterisco (*). Realizando búsqueda especial");

            String textoSinAsteriscoInicial = searchText.substring(1);
            System.out.println(textoSinAsteriscoInicial);
            naves = tipoNaveService.buscarTipoNavesQueTerminenCon(textoSinAsteriscoInicial);

        } else if (searchText.endsWith("*")) {

            log.info("Texto de búsqueda termina con un asterisco (*). Realizando búsqueda especial");
           
            String textoSinAsteriscoFinal = searchText.substring(0, searchText.length() - 1);
            System.out.println(textoSinAsteriscoFinal);
            naves = tipoNaveService.buscarTipoNavesQueEmpiecenCon(textoSinAsteriscoFinal);
        }
        else {
            log.info("Buscando personas cuyo apellido comienza con {}", searchText);
            naves = tipoNaveService.buscarNombre(searchText);
        }

        model.addAttribute("tipoNave", naves);
        return "TipoNave_CRUD/tipoNave-search";
    }

    @PostMapping(value = "/guardar")
    public String guardarTipoNave(@Valid TipoNave tipoNave, BindingResult result, Model model) throws  RepeatedNameException, NotNullException {
        String err2 = tipoNaveService.tipoNaveValidationNombre(tipoNave);
    
        if (result.hasErrors()|| !err2.isEmpty()) {
            
            if (!err2.isEmpty()) {
                throw new RepeatedNameException(err2);
            }  

            return "TipoNave_CRUD/tipoNave-edit"; // Regresa a la vista para mostrar los errores
        }
    
        tipoNaveService.guardarTipoNave(tipoNave);
        return "redirect:/tipoNave/list";
    }

    @GetMapping("/borrar-form/{id}")
    public String borrarFormEstrella(Model model, @PathVariable Long id)
    {
        TipoNave nave = tipoNaveService.recuperarTipoNave(id);
        model.addAttribute("nave", nave);
        return "TipoNave_CRUD/tipoNave-delete";
    }

    @PostMapping("/borrar")
    public String borrarEstrella(@Valid TipoNave nave, BindingResult result, Model model) throws UnableToDeletePlanetaException
    {
        /*
        String err= tipoNaveService.estrellaValidationPlaneta(nave);

        if(!err.isEmpty())
        {
            System.out.println(err);
            throw new UnableToDeletePlanetaException(err);
        }
        
        
         */
        tipoNaveService.borrarTipoNave(nave);
        return "redirect:/nave/menu";
    }

    @PostMapping("/crear")
    public String crearEstrella(@Valid TipoNave tipoNave, BindingResult result, Model model) throws RepeatedNameException, RepeatedCoordinateException, NotNullException, OutOfLimitsException
    {
       //estrellaService
        String err2 = tipoNaveService.tipoNaveValidationNombre(tipoNave);
    
        if (result.hasErrors() || !err2.isEmpty()) {
            
            if (!err2.isEmpty()) {
                throw new RepeatedNameException(err2);
            }  

            return "TipoNave_CRUD/tipoNave-crear"; // Regresa a la vista para mostrar los errores
        }

        
        tipoNaveService.crearTipoNave(tipoNave);

        return "redirect:/tipoNave/menu";
    }

    @RequestMapping("/creador")
    public String creador(Model model) {
        model.addAttribute("tipoNave", new TipoNave());
        return "TipoNave_CRUD/tipoNave-create";
    }

    @RequestMapping("/menu")
    public String menu() {
        return "TipoNave_CRUD/tipoNave-menu";
    }

    @RequestMapping("/researcher")
    public String buscador() {
        return "TipoNave_CRUD/tipoNave-search";
    }

    @RequestMapping("/listar")
    public String listar() {
        return "redirect:/tipoNave/list";
    }
}
