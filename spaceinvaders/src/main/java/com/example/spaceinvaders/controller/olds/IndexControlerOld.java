package com.example.spaceinvaders.controller.olds;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexControlerOld {
   
    @RequestMapping("/")
    public String index()
    {
        return "index";
    }
    
}
