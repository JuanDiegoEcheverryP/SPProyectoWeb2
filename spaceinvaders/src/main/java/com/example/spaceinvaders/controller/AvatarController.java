package com.example.spaceinvaders.controller;

import com.example.spaceinvaders.model.Avatar;
import com.example.spaceinvaders.services.AvatarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avatar")
public class AvatarController {

    private final AvatarService avatarService;

    @Autowired
    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    //localhost:8080/avatar/listar
    @GetMapping
    public List<Avatar> findAll() {
        return avatarService.getAll();
    }

    //localhost:8080/avatar/buscarPorId/x
    @GetMapping("buscarPorId/{id}")
    public Avatar findById(@PathVariable Long id) {
        return avatarService.getById(id);
    }

    //http://localhost:8080/avatar/crear/?nombre="avatarX"&imagen="urlX"
    //En postman puedes ponerlo en params de la siguiente forma:
    //      Key             Value
    //      nombre          avatarX
    //      imagen          urlX
    @PostMapping("/crear")
    public Avatar create(@RequestParam("nombre") String name, @RequestParam("imagen") String url) {
        Avatar newAvatar = new Avatar(name,url);
        return avatarService.create(newAvatar);
    }

    //http://localhost:8080/avatar/actualizar/id?nombre="avatarX"&imagen="urlX"
    //En postman puedes ponerlo en params de la siguiente forma:
    //      Key             Value
    //      nombre          avatarX
    //      imagen          urlX
    @PutMapping("/actualizar/{id}")
    public Avatar update(@PathVariable Long id, @RequestParam("nombre") String name, @RequestParam("imagen") String url) {
        Avatar avatar = new Avatar(name,url);
        return avatarService.update(id, avatar);
    }

    //localhost:8080/avatar/borrar/id
    @DeleteMapping("/borrar/{id}")
    public void delete(@PathVariable Long id) {
        avatarService.delete(id);
    }
}

//La herramienta postman es muy util para ver que funcionen estas rutas