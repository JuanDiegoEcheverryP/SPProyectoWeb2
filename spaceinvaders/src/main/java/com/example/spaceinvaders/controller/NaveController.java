package com.example.spaceinvaders.controller;

import com.example.spaceinvaders.model.Nave;
import com.example.spaceinvaders.model.Nave;
import com.example.spaceinvaders.services.NaveService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/list")
    public String listarNaves(Model model) {
        List<Nave> naves = naveService.obtenerTodasLasNaves();
        model.addAttribute("naves", naves);
        return "Nave_CRUD/Nave-list"; // Devuelve el nombre de la plantilla HTML
    }

    @GetMapping("/editar/{id}")
    public String editarNave(Model model, @PathVariable Long id) {
        Nave nave = naveService.recuperarNave(id);
        model.addAttribute("naves", nave);
        return "Nave_CRUD/nave-search";
    }

    @GetMapping("/ver/{idNave}")
    String verNave(Model model, @PathVariable("idNave") Long id) {
        Nave nave = naveService.recuperarNave(id);
        model.addAttribute("nave", nave);
        return "Nave_CRUD/nave-view";
    }

    @GetMapping("/search")
    public String listNave(@RequestParam(required = false) String searchText, Model model) {
        log.info("Solicitud GET recibida en /nave/search");

        List<Nave> naves=new ArrayList<>();

        if (searchText == null || searchText.trim().equals("")) {
            log.info("No hay texto de búsqueda. Retornando todo");
            naves= naveService.obtenerTodasLasNaves();
        } 
        else if (searchText.startsWith("*") && searchText.endsWith("*")) {
            
            log.info("Texto de búsqueda inicia y termina con un asterisco (*). Realizando búsqueda especial");

            String textoSinAsteriscos = searchText.substring(1, searchText.length() - 1);
            System.out.println(textoSinAsteriscos);
            naves = naveService.buscarNavesQueContengan(textoSinAsteriscos);


        } else if (searchText.startsWith("*")) {
            // El texto de búsqueda inicia con un asterisco (*)
            log.info("Texto de búsqueda inicia con un asterisco (*). Realizando búsqueda especial");

            String textoSinAsteriscoInicial = searchText.substring(1);
            System.out.println(textoSinAsteriscoInicial);
            naves = naveService.buscarNavesQueTerminenCon(textoSinAsteriscoInicial);

        } else if (searchText.endsWith("*")) {

            log.info("Texto de búsqueda termina con un asterisco (*). Realizando búsqueda especial");
           
            String textoSinAsteriscoFinal = searchText.substring(0, searchText.length() - 1);
            System.out.println(textoSinAsteriscoFinal);
            naves = naveService.buscarNavesQueEmpiecenCon(textoSinAsteriscoFinal);
        }
        else {
            log.info("Buscando navees cuyo apellido comienza con {}", searchText);
            naves = naveService.buscarNombre(searchText);
        }

        model.addAttribute("naves", naves);
        return "Nave_CRUD/nave-search";
    }
    
    @RequestMapping("/searcher")
    public String buscador() {
        return "Nave_CRUD/nave-search";
    }
}
