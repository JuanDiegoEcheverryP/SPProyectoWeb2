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

import com.example.spaceinvaders.exceptions.NotNullException;
import com.example.spaceinvaders.exceptions.RepeatedNameException;
import com.example.spaceinvaders.model.Producto;
import com.example.spaceinvaders.services.PlanetaService;
import com.example.spaceinvaders.services.ProductoService;

@Controller
@RequestMapping("/producto")
public class ProductoControler {
    
    Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private ProductoService  productoService;
}

