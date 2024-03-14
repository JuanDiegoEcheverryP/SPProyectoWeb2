package com.example.spaceinvaders.controller.olds;

import com.example.spaceinvaders.model.ProductoBodega;
import com.example.spaceinvaders.services.ProductoBodegaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/productoBodegas")
public class ProductoBodegaController {

    @Autowired
    private ProductoBodegaService productoBodegaService;

    @GetMapping("/list")
    public String listarProductoBodegas(Model model) {
        List<ProductoBodega> productoBodegas = productoBodegaService.obtenerTodasLasProductoBodegas();
        model.addAttribute("productoBodegas", productoBodegas);
        return "ProductoBodega_CRUD/ProductoBodega-list"; // Devuelve el nombre de la plantilla HTML
    }
}
