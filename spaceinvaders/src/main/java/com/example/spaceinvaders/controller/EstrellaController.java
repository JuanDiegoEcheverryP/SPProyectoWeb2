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

import com.example.spaceinvaders.model.Estrella;
import com.example.spaceinvaders.services.EstrellaService;

@Controller
@RequestMapping("/estrella")
public class EstrellaController {
    
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
        return "Estrella_CRUD/estrella-search";
    }

    @GetMapping("/ver/{idEstrella}")
    String verPersona(Model model, @PathVariable("idEstrella") Long id) {
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
    
    @RequestMapping("/searcher")
    public String buscador() {
        return "Estrella_CRUD/estrella-search";
    }

     /* @PostMapping(value = "/save")
    public String guardarPersona(@Valid Estrella estrella, BindingResult result, Model model) {
        if (result.hasErrors()) {
     return "Estrella_CRUD/estrella-edit";
        }
        estrellaService.guardarPersona(estrella);
        return "redirect:/person/list";
    }*/ 
    
}
