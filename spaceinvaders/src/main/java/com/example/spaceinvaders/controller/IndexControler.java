package com.example.spaceinvaders.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexControler {
   
    @RequestMapping("/")
    public String index()
    {
        return "index";
    }
    
}
