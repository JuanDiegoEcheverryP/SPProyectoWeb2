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

import com.example.spaceinvaders.model.Avatar;
import com.example.spaceinvaders.model.Avatar;
import com.example.spaceinvaders.services.AvatarService;

@Controller
@RequestMapping("/avatar")
public class AvatarController {

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private AvatarService avatarService;

    @GetMapping("/list")
    public String listarAvatares(Model model) {
        List<Avatar> avatar = avatarService.listarAvatars();
        model.addAttribute("avatar", avatar);
        return "Avatar_CRUD/avatar-list";
    }

    
    @GetMapping("/editar/{id}")
    public String editarAvatar(Model model, @PathVariable Long id) {
        Avatar avatar = avatarService.recuperarAvatar(id);
        model.addAttribute("avatars", avatar);
        return "Avatar_CRUD/avatar-search";
    }

    @GetMapping("/ver/{idAvatar}")
    String verAvatar(Model model, @PathVariable("idAvatar") Long id) {
        Avatar avatar = avatarService.recuperarAvatar(id);
        model.addAttribute("avatar", avatar);
        return "Avatar_CRUD/avatar-view";
    }

    @GetMapping("/search")
    public String listAvatar(@RequestParam(required = false) String searchText, Model model) {
        log.info("Solicitud GET recibida en /avatar/search");

        List<Avatar> avatars=new ArrayList<>();

        if (searchText == null || searchText.trim().equals("")) {
            log.info("No hay texto de búsqueda. Retornando todo");
            avatars= avatarService.listarAvatars();
        } 
        else if (searchText.startsWith("*") && searchText.endsWith("*")) {
            
            log.info("Texto de búsqueda inicia y termina con un asterisco (*). Realizando búsqueda especial");

            String textoSinAsteriscos = searchText.substring(1, searchText.length() - 1);
            System.out.println(textoSinAsteriscos);
            avatars = avatarService.buscarAvatarsQueContengan(textoSinAsteriscos);


        } else if (searchText.startsWith("*")) {
            // El texto de búsqueda inicia con un asterisco (*)
            log.info("Texto de búsqueda inicia con un asterisco (*). Realizando búsqueda especial");

            String textoSinAsteriscoInicial = searchText.substring(1);
            System.out.println(textoSinAsteriscoInicial);
            avatars = avatarService.buscarAvatarsQueTerminenCon(textoSinAsteriscoInicial);

        } else if (searchText.endsWith("*")) {

            log.info("Texto de búsqueda termina con un asterisco (*). Realizando búsqueda especial");
           
            String textoSinAsteriscoFinal = searchText.substring(0, searchText.length() - 1);
            System.out.println(textoSinAsteriscoFinal);
            avatars = avatarService.buscarAvatarsQueEmpiecenCon(textoSinAsteriscoFinal);
        }
        else {
            log.info("Buscando avatares cuyo apellido comienza con {}", searchText);
            avatars = avatarService.buscarNombre(searchText);
        }

        model.addAttribute("avatars", avatars);
        return "Avatar_CRUD/avatar-search";
    }
    
    @RequestMapping("/searcher")
    public String buscador() {
        return "Avatar_CRUD/avatar-search";
    }

}
