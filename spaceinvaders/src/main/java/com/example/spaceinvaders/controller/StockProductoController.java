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
import com.example.spaceinvaders.exceptions.OutOfLimitsException;
import com.example.spaceinvaders.exceptions.RepeatedCoordinateException;
import com.example.spaceinvaders.exceptions.RepeatedNameException;
import com.example.spaceinvaders.exceptions.UnableToDeletePlanetaException;
import com.example.spaceinvaders.model.Planeta;
import com.example.spaceinvaders.model.Producto;
import com.example.spaceinvaders.model.Stock_planeta;
import com.example.spaceinvaders.services.PlanetaService;
import com.example.spaceinvaders.services.ProductoService;
import com.example.spaceinvaders.services.StockPlanetaService;

@Controller
@RequestMapping("/api/stock-planeta")
public class StockProductoController {

        
    Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private StockPlanetaService stockPlanetaService;

    @Autowired
    private PlanetaService planetaService;

    @Autowired
    private ProductoService productoService;

    @GetMapping("/list")
    public String listarStockPlaneta(Model model) {
        List<Stock_planeta> stockPlanetas = stockPlanetaService.listaStockPlaneta();
        model.addAttribute("stock", stockPlanetas);
        return "StockPlaneta_CRUD/stock-list";
    }

    @GetMapping("/editar/{id}")
    public String editarStockPlaneta(Model model, @PathVariable Long id) {
        Stock_planeta stockPlaneta = stockPlanetaService.recuperarStockPlaneta(id);
        model.addAttribute("stock", stockPlaneta);
        return "StockPlaneta_CRUD/stock-edit";
    }

    @GetMapping("/ver/{idStockPlaneta}")
    String verPersona(Model model, @PathVariable("idStockPlaneta") Long id) {
        Stock_planeta stockPlaneta = stockPlanetaService.recuperarStockPlaneta(id);
        model.addAttribute("stock", stockPlaneta);
        return "StockPlaneta_CRUD/stock-view";
    }

    @GetMapping("/search")
    public String listStockPlaneta(@RequestParam(required = false) String searchText, Model model) {
        log.info("Solicitud GET recibida en /stockPlaneta/search");

        List<Stock_planeta> stockPlanetas=new ArrayList<>();

        if (isNumeric(searchText)) {
            log.info("es numerico"+ searchText);
            Long numericValue = Long.parseLong(searchText); // Convertir a int si es necesario
            // Hacer algo con numericValue
            stockPlanetas.add(stockPlanetaService.recuperarStockPlaneta(numericValue));
        }
        else if (searchText == null || searchText.trim().equals("")) {
            log.info("No hay texto de búsqueda. Retornando todo");
            stockPlanetas= stockPlanetaService.listaStockPlaneta();
        } 
        else if (searchText.startsWith("*") && searchText.endsWith("*")) {
            
            log.info("Texto de búsqueda inicia y termina con un asterisco (*). Realizando búsqueda especial");

            String textoSinAsteriscos = searchText.substring(1, searchText.length() - 1);
            System.out.println(textoSinAsteriscos);
            stockPlanetas = stockPlanetaService.buscarStockPlanetaQueContengan(textoSinAsteriscos);


        } else if (searchText.startsWith("*")) {
            // El texto de búsqueda inicia con un asterisco (*)
            log.info("Texto de búsqueda inicia con un asterisco (*). Realizando búsqueda especial");

            String textoSinAsteriscoInicial = searchText.substring(1);
            System.out.println(textoSinAsteriscoInicial);
            stockPlanetas = stockPlanetaService.buscarStockPlanetaQueTerminenCon(textoSinAsteriscoInicial);

        } else if (searchText.endsWith("*")) {

            log.info("Texto de búsqueda termina con un asterisco (*). Realizando búsqueda especial");
           
            String textoSinAsteriscoFinal = searchText.substring(0, searchText.length() - 1);
            System.out.println(textoSinAsteriscoFinal);
            stockPlanetas = stockPlanetaService.buscarStockPlanetaQueEmpiecenCon(textoSinAsteriscoFinal);
        }
        else
        {
            stockPlanetas = stockPlanetaService.buscarNombre(searchText);
        }

        model.addAttribute("stock", stockPlanetas);
        return "StockPlaneta_CRUD/stock-search";
    }

    private boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    @PostMapping(value = "/guardar")
    public String guardarStockPlaneta(@Valid Stock_planeta stockPlaneta, BindingResult result, Model model) throws RepeatedCoordinateException, RepeatedNameException, NotNullException, OutOfLimitsException {
    
        if (result.hasErrors()) { 
            return "StockPlaneta_CRUD/stockPlaneta-edit"; // Regresa a la vista para mostrar los errores
        }
        else if (stockPlaneta.getFactorDemanda()==null || stockPlaneta.getFactorOferta()==null || stockPlaneta.getStock()==null)
        {
            throw new NotNullException("todos los campos deben estar llenos");
        }
        else if(stockPlaneta.getFactorDemanda()<0 || stockPlaneta.getFactorDemanda()>1000000 || stockPlaneta.getFactorOferta()<0 || stockPlaneta.getFactorOferta()>1000000)
        {
            throw new OutOfLimitsException("Los factores deben estar entre 0 y 1000000 (un millon)");
        }
    
        stockPlanetaService.guardarStockPlaneta(stockPlaneta);
        return "redirect:/stock-planeta/menu";
    }
    
    @GetMapping("/borrar-form/{id}")
    public String borrarFormStockPlaneta(Model model, @PathVariable Long id)
    {
        Stock_planeta stockPlaneta = stockPlanetaService.recuperarStockPlaneta(id);
        model.addAttribute("stock", stockPlaneta);
        return "StockPlaneta_CRUD/stock-delete";
    }

    @PostMapping("/borrar")
    public String borrarStockPlaneta(@Valid Stock_planeta stockPlaneta, BindingResult result, Model model) throws UnableToDeletePlanetaException
    {
        stockPlanetaService.borrarStockPlaneta(stockPlaneta);

        return "redirect:/stock-planeta/menu";
    }

    @PostMapping("/crear")
    public String crearStockPlaneta(@Valid Stock_planeta stockPlaneta, BindingResult result, Model model) throws RepeatedNameException, RepeatedCoordinateException, NotNullException, OutOfLimitsException
    {
       //stockPlanetaService
       String err = stockPlanetaService.stockPlanetaValidationRepeated(stockPlaneta);
    
        if (result.hasErrors() || !err.isEmpty()) {
            
           
            if (!err.isEmpty()) {
                throw new RepeatedNameException(err);
            }   

            return "StockPlaneta_CRUD/stock-crear"; // Regresa a la vista para mostrar los errores
        }
        else if (stockPlaneta.getFactorDemanda()==null || stockPlaneta.getFactorOferta()==null || stockPlaneta.getStock()==null)
        {
            throw new NotNullException("todos los campos deben estar llenos");
        }
        else if(stockPlaneta.getFactorDemanda()<0 || stockPlaneta.getFactorDemanda()>1000000 || stockPlaneta.getFactorOferta()<0 || stockPlaneta.getFactorOferta()>1000000)
        {
            throw new OutOfLimitsException("Los factores deben estar entre 0 y 1000000 (un millon)");
        }

        stockPlanetaService.crearStockPlaneta(stockPlaneta);

        return "redirect:/stock-planeta/menu";
    }

    @RequestMapping("/creador")
    public String creador(Model model) {
        model.addAttribute("stock", new Stock_planeta());
        List<Producto> productosTodos=productoService.listaProductos();
        List<Planeta>  planetasHabitados=planetaService.listaPlanetas();
        model.addAttribute("productos", productosTodos);
        model.addAttribute("planetas", planetasHabitados);
        return "StockPlaneta_CRUD/stock-create";
    }

    @RequestMapping("/menu")
    public String menu() {
        return "StockPlaneta_CRUD/stock-menu";
    }

    @RequestMapping("/researcher")
    public String buscador() {
        return "StockPlaneta_CRUD/stock-search";
    }

    /*@RequestMapping("/listar")
    public String listar() {
        return "redirect:/stock-planeta/list";
    }*/
    
}
