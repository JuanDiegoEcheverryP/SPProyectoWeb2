package com.example.spaceinvaders.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Avatar> listarAvatares() {
        return avatarService.listarAvatars();
    } 

    @GetMapping("/{id}")
    public Avatar findAvatar(@PathVariable Long id) {
        return avatarService.recuperarAvatar(id);
    } 
    
}
