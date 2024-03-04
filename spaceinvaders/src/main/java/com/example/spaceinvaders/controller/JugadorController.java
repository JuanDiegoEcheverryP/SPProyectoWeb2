package com.example.spaceinvaders.controller;

import com.example.spaceinvaders.exceptions.NotNullException;
import com.example.spaceinvaders.exceptions.RepeatedCoordinateException;
import com.example.spaceinvaders.exceptions.RepeatedNameException;
import com.example.spaceinvaders.exceptions.UnableToDeletePlanetaException;
import com.example.spaceinvaders.model.Estrella;
import com.example.spaceinvaders.model.Jugador;
import com.example.spaceinvaders.model.Nave;
import com.example.spaceinvaders.model.Jugador;
import com.example.spaceinvaders.services.JugadorService;

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
@RequestMapping("/jugador")
public class JugadorController {

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private JugadorService jugadorService;

    @GetMapping("/list")
    public String listarJugadors(Model model) {
        List<Jugador> jugadors = jugadorService.obtenerTodasLasJugadors();
        model.addAttribute("jugadores", jugadors);
        return "Jugador_CRUD/jugador-list";
    }
        
    @GetMapping("/editar/{id}")
    public String editarJugador(Model model, @PathVariable Long id) {
        Jugador jugador = jugadorService.recuperarJugador(id);
        model.addAttribute("jugador", jugador);
        return "Jugador_CRUD/jugador-edit";
    }

    @GetMapping("/ver/{idJugador}")
    String verJugador(Model model, @PathVariable("idJugador") Long id) {
        Jugador jugador = jugadorService.recuperarJugador(id);
        model.addAttribute("jugador", jugador);
        return "Jugador_CRUD/jugador-view";
    }

    @GetMapping("/search")
    public String listJugador(@RequestParam(required = false) String searchText, Model model) {
        log.info("Solicitud GET recibida en /jugador/search");

        List<Jugador> jugadors=new ArrayList<>();

        if (searchText == null || searchText.trim().equals("")) {
            log.info("No hay texto de búsqueda. Retornando todo");
            jugadors= jugadorService.obtenerTodasLasJugadors();
        } 
        else if (searchText.startsWith("*") && searchText.endsWith("*")) {
            
            log.info("Texto de búsqueda inicia y termina con un asterisco (*). Realizando búsqueda especial");

            String textoSinAsteriscos = searchText.substring(1, searchText.length() - 1);
            System.out.println(textoSinAsteriscos);
            jugadors = jugadorService.buscarJugadorsQueContengan(textoSinAsteriscos);


        } else if (searchText.startsWith("*")) {
            // El texto de búsqueda inicia con un asterisco (*)
            log.info("Texto de búsqueda inicia con un asterisco (*). Realizando búsqueda especial");

            String textoSinAsteriscoInicial = searchText.substring(1);
            System.out.println(textoSinAsteriscoInicial);
            jugadors = jugadorService.buscarJugadorsQueTerminenCon(textoSinAsteriscoInicial);

        } else if (searchText.endsWith("*")) {

            log.info("Texto de búsqueda termina con un asterisco (*). Realizando búsqueda especial");
           
            String textoSinAsteriscoFinal = searchText.substring(0, searchText.length() - 1);
            System.out.println(textoSinAsteriscoFinal);
            jugadors = jugadorService.buscarJugadorsQueEmpiecenCon(textoSinAsteriscoFinal);
        }
        else {
            log.info("Buscando jugadores cuyo apellido comienza con {}", searchText);
            jugadors = jugadorService.buscarNombre(searchText);
        }

        model.addAttribute("jugadors", jugadors);
        return "Jugador_CRUD/jugador-search";
    }

    @PostMapping(value = "/guardar")
    public String guardarNave(@Valid Jugador jugador, BindingResult result, Model model) throws RepeatedNameException, NotNullException {
        String err = jugadorService.jugadorValidationNombre(jugador);
    
        if (result.hasErrors() || !err.isEmpty()) {
            
            if (!err.isEmpty()) {
                throw new RepeatedNameException(err);
            }    

            return "Jugador_CRUD/jugador-edit"; // Regresa a la vista para mostrar los errores
        }
    
        jugadorService.guardarJugador(jugador);
        return "redirect:/jugador/menu";
    }

    @GetMapping("/borrar-form/{id}")
    public String borrarFormJugador(Model model, @PathVariable Long id)
    {
        Jugador jugador = jugadorService.recuperarJugador(id);
        model.addAttribute("jugador", jugador);
        return "Jugador_CRUD/jugador-delete";
    }

    @PostMapping("/borrar")
    public String borrarJugador(@Valid Jugador jugador, BindingResult result, Model model) throws UnableToDeletePlanetaException
    {

        jugadorService.borrarJugador(jugador);

        return "redirect:/jugador/list";
    }
}
