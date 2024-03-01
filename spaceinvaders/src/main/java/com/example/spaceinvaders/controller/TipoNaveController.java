package com.example.spaceinvaders.controller;

import com.example.spaceinvaders.model.TipoNave;
import com.example.spaceinvaders.services.TipoNaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipoNave")
public class TipoNaveController {

    private final TipoNaveService tipoNaveService;

    @Autowired
    public TipoNaveController(TipoNaveService tipoNaveService) {
        this.tipoNaveService = tipoNaveService;
    }

    //localhost:8080/tipoNave/listar
    @GetMapping
    public List<TipoNave> findAll() {
        return tipoNaveService.getAll();
    }

    //localhost:8080/tipoNave/buscarPorId/x
    @GetMapping("buscarPorId/{id}")
    public TipoNave findById(@PathVariable Long id) {
        return tipoNaveService.getById(id);
    }

    //http://localhost:8080/tipoNave/crear/?nombre="tipoNaveX"&imagen="urlX"
    @PostMapping("/crear")
    public TipoNave create(@RequestParam("tipo") String tipo, @RequestParam("volBodega") float volBodega,@RequestParam("velocidad") float velocidad, @RequestParam("foto") String foto) {
        TipoNave newTipoNave = new TipoNave(tipo,volBodega,velocidad,foto);
        return tipoNaveService.create(newTipoNave);
    }

    //http://localhost:8080/tipoNave/actualizar/id?nombre="tipoNaveX"&imagen="urlX"
    @PutMapping("/actualizar/{id}")
    public TipoNave update(@PathVariable Long id, @RequestParam("tipo") String tipo, @RequestParam("volBodega") float volBodega,@RequestParam("velocidad") float velocidad, @RequestParam("foto") String foto) {
        TipoNave tipoNave = new TipoNave(tipo,volBodega,velocidad,foto);
        return tipoNaveService.update(id, tipoNave);
    }
    
    //localhost:8080/tipoNave/borrar/id
    @DeleteMapping("/borrar/{id}")
    public void delete(@PathVariable Long id) {
        tipoNaveService.delete(id);
    }
}

//La herramienta postman es muy util para ver que funcionen estas rutas