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

import com.example.spaceinvaders.model.Avatar;
import com.example.spaceinvaders.services.AvatarService;

@Controller
@RequestMapping("/avatar")
public class AvatarController {

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private AvatarService avatarService;

    @GetMapping("/list")
    public String listarPersonas(Model model) {
        List<Avatar> naves = avatarService.listarAvatars();
        model.addAttribute("avatar", naves);
        return "avatar-list";
    }
}
