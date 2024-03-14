package com.example.spaceinvaders.services;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spaceinvaders.model.Stock_planeta;
import com.example.spaceinvaders.model.Planeta;
import com.example.spaceinvaders.repository.StockPlanetaRepository;

@Service
public class StockPlanetaService {

    Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private StockPlanetaRepository stockPlanetaRepository;

    public List<Stock_planeta> listaStockPlaneta()
    {
        return stockPlanetaRepository.findAll();
    }

    public Stock_planeta recuperarStockPlaneta(Long id)
    {
        return stockPlanetaRepository.findById(id).orElseThrow();
    }

    public List<Stock_planeta> recuperarStockDePlaneta(Long id)
    {
        List<Stock_planeta> stockSinPrecios=stockPlanetaRepository.findByPlanetaId(id);
        
        return stockSinPrecios;
    }

    public Stock_planeta guardarStockPlaneta(Stock_planeta stockPlaneta)
    {

        return stockPlanetaRepository.save(stockPlaneta);
    }

    public void borrarStockPlaneta(Stock_planeta stockPlaneta)
    {
        stockPlanetaRepository.delete(stockPlaneta);
    }

    public List<Stock_planeta> buscarNombre(String textoBusqueda) {
        List<Stock_planeta> stock;
        stock=stockPlanetaRepository.findAllByPlanetaNombre(textoBusqueda);
        if(stock.isEmpty())
            stock=stockPlanetaRepository.findAllByProductoNombre(textoBusqueda);
        return stock;
    }

    public List<Stock_planeta> buscarStockPlanetaQueContengan(String textoBusqueda) {
        List<Stock_planeta> stock;
        stock=stockPlanetaRepository.findAllByPlanetaNombreContainingWith(textoBusqueda);
        if(stock.isEmpty())
            stock=stockPlanetaRepository.findAllByProductoNombreContainingWith(textoBusqueda);
        return stock;

    }

    public List<Stock_planeta> buscarStockPlanetaQueTerminenCon(String textoBusqueda) {
        List<Stock_planeta> stock;
        stock=stockPlanetaRepository.findAllByPlanetaNombreEndingWith(textoBusqueda);
        if(stock.isEmpty())
            stock=stockPlanetaRepository.findAllByProductoNombreEndingWith(textoBusqueda);
        return stock;
    }

    public List<Stock_planeta> buscarStockPlanetaQueEmpiecenCon(String textoBusqueda) {
        List<Stock_planeta> stock;
        stock=stockPlanetaRepository.findAllByPlanetaNombreStartingWith(textoBusqueda);
        if(stock.isEmpty())
            stock=stockPlanetaRepository.findAllByProductoNombreStartingWith(textoBusqueda);
        return stock;
    }

    public Stock_planeta crearStockPlaneta(Stock_planeta stockPlaneta) {

        Stock_planeta nueva=stockPlanetaRepository.save(stockPlaneta);
        return nueva;
    }

    public String stockPlanetaValidationRepeated(Stock_planeta stockPlaneta) {
        
        String mensaje="";
        List<Stock_planeta> res=stockPlanetaRepository.findExact(stockPlaneta.getPlaneta().getId(),stockPlaneta.getProducto().getId());
        
        if(!res.isEmpty())
        {
            mensaje="no se puede crear el stock de ese planeta y producto porque este ya existe en la bdd";
        }

        return mensaje;
    }


    public void deleteAllByPlaneta(Planeta planeta)
    {
        List<Stock_planeta> borrar=stockPlanetaRepository.findExactByPlanetas(planeta.getId());

        if (borrar != null) { // Verificar si la lista no es nula
            for (Stock_planeta planetaBorrar : borrar) {
                stockPlanetaRepository.delete(planetaBorrar);
            }
        }
    }
}
