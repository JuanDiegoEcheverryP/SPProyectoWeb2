package com.example.spaceinvaders.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.spaceinvaders.model.Avatar;
import com.example.spaceinvaders.services.AvatarService;

@RestController
@RequestMapping("/api/avatar")
public class AvatarController {

    @Autowired
    private AvatarService avatarService;

    @GetMapping("/list")
    public String listarAvatares(Model model) {
        List<Avatar> avatar = avatarService.listarAvatars();
        model.addAttribute("avatar", avatar);
        return "Avatar_CRUD/avatar-list";
    } 
}
