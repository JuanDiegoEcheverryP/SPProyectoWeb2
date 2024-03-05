package com.example.spaceinvaders.controller;

import com.example.spaceinvaders.exceptions.NotNullException;
import com.example.spaceinvaders.exceptions.RepeatedCoordinateException;
import com.example.spaceinvaders.exceptions.RepeatedNameException;
import com.example.spaceinvaders.exceptions.UnableToDeleteJugadorException;
import com.example.spaceinvaders.exceptions.UnableToDeletePlanetaException;
import com.example.spaceinvaders.model.Nave;
import com.example.spaceinvaders.model.Planeta;
import com.example.spaceinvaders.model.TipoNave;
import com.example.spaceinvaders.services.NaveService;
import com.example.spaceinvaders.services.PlanetaService;
import com.example.spaceinvaders.services.TipoNaveService;

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

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/nave")
public class NaveController {

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private NaveService naveService;

    @Autowired
    private TipoNaveService tipoNaveService;

    @Autowired
    private PlanetaService planetaService;


    @GetMapping("/list")
    public String listarNaves(Model model) {
        List<Nave> naves = naveService.listaNaves();
        model.addAttribute("naves", naves);
        return "Nave_CRUD/Nave-list"; // Devuelve el nombre de la plantilla HTML
    }

    @GetMapping("/editar/{id}")
    public String editarNave(Model model, @PathVariable Long id) {
        Nave nave = naveService.recuperarNave(id);
        model.addAttribute("nave", nave);
        List<TipoNave>  tipoNaveTodos=tipoNaveService.listarTipoNaves();
        List<Planeta>  planetaTodos=planetaService.listaPlanetas();
        model.addAttribute("tipoNaves", tipoNaveTodos);
        model.addAttribute("planetas", planetaTodos);
        return "Nave_CRUD/nave-edit";
    }

    @GetMapping("/ver/{id}")
    String verNave(Model model, @PathVariable("id") Long id) {
        Nave nave = naveService.recuperarNave(id);
        model.addAttribute("nave", nave);
        return "Nave_CRUD/nave-view";
    }

    @GetMapping("/search")
    public String listNave(@RequestParam(required = false) String searchText, Model model) {

        List<Nave> nave=new ArrayList<>();

        if (searchText == null || searchText.trim().equals("")) {
            log.info("No hay texto de búsqueda. Retornando todo");
            nave= naveService.listaNaves();
        } 
        else if (searchText.startsWith("*") && searchText.endsWith("*")) {
            
            log.info("Texto de búsqueda inicia y termina con un asterisco (*). Realizando búsqueda especial");

            String textoSinAsteriscos = searchText.substring(1, searchText.length() - 1);
            System.out.println(textoSinAsteriscos);
            nave = naveService.buscarNavesQueContengan(textoSinAsteriscos);


        } else if (searchText.startsWith("*")) {
            // El texto de búsqueda inicia con un asterisco (*)
            log.info("Texto de búsqueda inicia con un asterisco (*). Realizando búsqueda especial");

            String textoSinAsteriscoInicial = searchText.substring(1);
            System.out.println(textoSinAsteriscoInicial);
            nave = naveService.buscarNavesQueTerminenCon(textoSinAsteriscoInicial);

        } else if (searchText.endsWith("*")) {

            log.info("Texto de búsqueda termina con un asterisco (*). Realizando búsqueda especial");
           
            String textoSinAsteriscoFinal = searchText.substring(0, searchText.length() - 1);
            System.out.println(textoSinAsteriscoFinal);
            nave = naveService.buscarNavesQueEmpiecenCon(textoSinAsteriscoFinal);
        }
        else {
            log.info("Buscando personas cuyo apellido comienza con {}", searchText);
            nave = naveService.buscarNombre(searchText);
        }

        model.addAttribute("naves", nave);
        return "Nave_CRUD/nave-search";
    }

    @PostMapping(value = "/guardar")
    public String guardarNave(@Valid Nave nave, BindingResult result, Model model) throws RepeatedCoordinateException, RepeatedNameException, NotNullException {
        String err = naveService.naveValidationNombre(nave);
    
        if (result.hasErrors() || !err.isEmpty()) {
            
            if (!err.isEmpty()) {
                throw new RepeatedNameException(err);
            }    

            return "Nave_CRUD/nave-edit"; // Regresa a la vista para mostrar los errores
        }
    
        naveService.guardarNave(nave);
        return "redirect:/nave/menu";
    }

    @GetMapping("/borrar-form/{id}")
    public String borrarFormNave(Model model, @PathVariable Long id)
    {
        Nave nave = naveService.recuperarNave(id);
        model.addAttribute("nave", nave);
        return "Nave_CRUD/nave-delete";
    }

    @PostMapping("/borrar")
    public String borrarNave(@Valid Nave nave, BindingResult result, Model model) throws UnableToDeletePlanetaException
    {
        
        //Falta esto
        String err= naveService.naveValidarBorrar(nave);

        if(!err.isEmpty())
        {
            System.out.println(err);
            throw new UnableToDeletePlanetaException(err);
        }
        
        naveService.borrarNave(nave);

        return "redirect:/nave/menu";
    }

    @PostMapping("/crear")
    public String crearNave(@Valid Nave nave, BindingResult result, Model model) throws RepeatedNameException, RepeatedCoordinateException, NotNullException
    {
        String err2 = naveService.naveValidationNombre(nave);
    
        if (result.hasErrors() || !err2.isEmpty()) {
            
            if (!err2.isEmpty()) {
                throw new RepeatedNameException(err2);
            }   

            return "Nave_CRUD/nave-crear"; // Regresa a la vista para mostrar los errores
        }

        
        naveService.crearNave(nave);

        return "redirect:/nave/menu";
    }

    @RequestMapping("/creador")
    public String creador(Model model) {
        model.addAttribute("nave", new Nave());
        List<TipoNave>  tipoNaveTodos=tipoNaveService.listarTipoNaves();
        List<Planeta>  planetaTodos=planetaService.listaPlanetas();
        model.addAttribute("tipoNaves", tipoNaveTodos);
        model.addAttribute("planetas", planetaTodos);
        return "Nave_CRUD/nave-create";
    }

    @RequestMapping("/menu")
    public String menu() {
        return "Nave_CRUD/nave-menu";
    }

    @RequestMapping("/researcher")
    public String buscador() {
        return "Nave_CRUD/nave-search";
    }

    @RequestMapping("/listar")
    public String listar() {
        return "redirect:/nave/list";
    }
}
