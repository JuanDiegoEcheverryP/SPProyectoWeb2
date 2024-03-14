package com.example.spaceinvaders.controller.olds;
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
import com.example.spaceinvaders.exceptions.RepeatedCoordinateException;
import com.example.spaceinvaders.exceptions.RepeatedNameException;
import com.example.spaceinvaders.exceptions.UnableToDeletePlanetaException;
import com.example.spaceinvaders.model.Producto;
import com.example.spaceinvaders.services.ProductoService;

@Controller
@RequestMapping("/producto")
public class ProductoControler {
    
    Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private ProductoService  productoService;

    @GetMapping("/list")
    public String listarProductos(Model model) {
        List<Producto> productos = productoService.listaProductos();
        model.addAttribute("producto", productos);
        return "Producto_CRUD/producto-list";
    }

    @GetMapping("/editar/{id}")
    public String editarProducto(Model model, @PathVariable Long id) {
        Producto producto = productoService.recuperarProducto(id);
        model.addAttribute("producto", producto);
        return "Producto_CRUD/producto-edit";
    }

    @GetMapping("/ver/{idProducto}")
    String verPersona(Model model, @PathVariable("idProducto") Long id) {
        Producto producto = productoService.recuperarProducto(id);
        model.addAttribute("producto", producto);
        return "Producto_CRUD/producto-view";
    }

    @GetMapping("/search")
    public String listProducto(@RequestParam(required = false) String searchText, Model model) {
        log.info("Solicitud GET recibida en /producto/search");

        List<Producto> productos=new ArrayList<>();

        if (searchText == null || searchText.trim().equals("")) {
            log.info("No hay texto de búsqueda. Retornando todo");
            productos= productoService.listaProductos();
        } 
        else if (searchText.startsWith("*") && searchText.endsWith("*")) {
            
            log.info("Texto de búsqueda inicia y termina con un asterisco (*). Realizando búsqueda especial");

            String textoSinAsteriscos = searchText.substring(1, searchText.length() - 1);
            System.out.println(textoSinAsteriscos);
            productos = productoService.buscarProductosQueContengan(textoSinAsteriscos);


        } else if (searchText.startsWith("*")) {
            // El texto de búsqueda inicia con un asterisco (*)
            log.info("Texto de búsqueda inicia con un asterisco (*). Realizando búsqueda especial");

            String textoSinAsteriscoInicial = searchText.substring(1);
            System.out.println(textoSinAsteriscoInicial);
            productos = productoService.buscarProductosQueTerminenCon(textoSinAsteriscoInicial);

        } else if (searchText.endsWith("*")) {

            log.info("Texto de búsqueda termina con un asterisco (*). Realizando búsqueda especial");
           
            String textoSinAsteriscoFinal = searchText.substring(0, searchText.length() - 1);
            System.out.println(textoSinAsteriscoFinal);
            productos = productoService.buscarProductosQueEmpiecenCon(textoSinAsteriscoFinal);
        }
        else {
            log.info("Buscando personas cuyo apellido comienza con {}", searchText);
            productos = productoService.buscarNombre(searchText);
        }

        model.addAttribute("productos", productos);
        return "Producto_CRUD/producto-search";
    }

    @PostMapping(value = "/guardar")
    public String guardarProducto(@Valid Producto producto, BindingResult result, Model model) throws RepeatedCoordinateException, RepeatedNameException, NotNullException {
        String err2 = productoService.productoValidationNombre(producto);
    
        if (result.hasErrors() || !err2.isEmpty()) {
            
            if (!err2.isEmpty()) {
                throw new RepeatedNameException(err2);
            }   

            return "Producto_CRUD/producto-edit"; // Regresa a la vista para mostrar los errores
        }
        else if (producto.getNombre()==null || producto.getVolumen()==null)
        {
            throw new NotNullException("todos los campos deben estar llenos");
        }
    
        productoService.guardarProducto(producto);
        return "redirect:/producto/menu";
    }
    
    @GetMapping("/borrar-form/{id}")
    public String borrarFormProducto(Model model, @PathVariable Long id)
    {
        Producto producto = productoService.recuperarProducto(id);
        model.addAttribute("producto", producto);
        return "Producto_CRUD/producto-delete";
    }

    @PostMapping("/borrar")
    public String borrarProducto(@Valid Producto producto, BindingResult result, Model model) throws UnableToDeletePlanetaException
    {
        String err= productoService.productoValidation(producto);

        if(!err.isEmpty())
        {
            System.out.println(err);
            throw new UnableToDeletePlanetaException(err);
        }
        
        productoService.borrarProducto(producto);

        return "redirect:/producto/menu";
    }

    @PostMapping("/crear")
    public String crearProducto(@Valid Producto producto, BindingResult result, Model model) throws RepeatedNameException, RepeatedCoordinateException, NotNullException
    {
       //productoService
        String err2 = productoService.productoValidationNombre(producto);
    
        if (result.hasErrors() || !err2.isEmpty()) {
            
            if (!err2.isEmpty()) {
                throw new RepeatedNameException(err2);
            }    

            return "Producto_CRUD/producto-crear"; // Regresa a la vista para mostrar los errores
        }
        else if (producto.getNombre()==null || producto.getVolumen()==null)
        {
            throw new NotNullException("todos los campos deben estar llenos");
        }
        
        productoService.crearProducto(producto);

        return "redirect:/producto/menu";
    }

    @RequestMapping("/creador")
    public String creador(Model model) {
        model.addAttribute("producto", new Producto());
        return "Producto_CRUD/producto-create";
    }

    @RequestMapping("/menu")
    public String menu() {
        return "Producto_CRUD/producto-menu";
    }

    @RequestMapping("/researcher")
    public String buscador() {
        return "Producto_CRUD/producto-search";
    }

   /*@RequestMapping("/listar")
    public String listar() {
        return "redirect:/producto/list";
    } */ 
}

