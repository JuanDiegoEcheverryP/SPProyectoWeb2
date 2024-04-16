package com.example.spaceinvaders.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spaceinvaders.model.Avatar;
import com.example.spaceinvaders.services.AvatarService;

@RestController
@RequestMapping("/api/avatar")
@CrossOrigin(origins = "http://localhost:4200/")
public class AvatarController {

    @Autowired
    private AvatarService avatarService;

    @GetMapping("/list")
    public ResponseEntity<?>  listarAvatares() {
        if(avatarService.listarAvatars()!=null)
        {
            return ResponseEntity.ok(avatarService.listarAvatars());
        }
        else
        {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay avatares");
        }
    } 

    @GetMapping("/{id}")
    public ResponseEntity<?> findAvatar(@PathVariable Long id) {
        try {
            Avatar avatar = avatarService.recuperarAvatar(id);
            return ResponseEntity.ok(avatar);
        } catch (NoSuchElementException e) {
            String errorMessage = "No existe un avatar con ese id";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }
    
}
