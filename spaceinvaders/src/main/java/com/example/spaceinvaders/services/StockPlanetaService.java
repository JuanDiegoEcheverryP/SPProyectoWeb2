package com.example.spaceinvaders.services;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spaceinvaders.model.Stock_planeta;
import com.example.spaceinvaders.model.DTO.ProductoDTO;
import com.example.spaceinvaders.model.Planeta;
import com.example.spaceinvaders.model.Producto;
import com.example.spaceinvaders.repository.StockPlanetaRepository;

@Service
public class StockPlanetaService {

    Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private StockPlanetaRepository stockPlanetaRepository;

    public boolean validarStockCompra(Long idProducto,Long idPlaneta, int cant)
    {
        if(cant<=stockPlanetaRepository.findStockByPlanetaIdAndProductoId(idPlaneta, idProducto))
            return true;

        return false;
    }  

    public void actualizarStock(Long idProducto,Long idPlaneta, int cant)
    {
        stockPlanetaRepository.actualizarStock(idPlaneta, idProducto, cant);
    }  

    public List<Stock_planeta> listaStockPlaneta()
    {
        return stockPlanetaRepository.findAll();
    }

    public Stock_planeta recuperarStockPlaneta(Long id)
    {
        return stockPlanetaRepository.findById(id).orElseThrow();
    }

    public List<ProductoDTO> recuperarStockDePlaneta(Long id)
    {
        List<Object[]> productos=stockPlanetaRepository.findProductsByPlanetaId(id);
        List<ProductoDTO> stockSinPrecios=new ArrayList<>();

        for (Object[] productoData : productos) {
            Stock_planeta stockPlaneta = (Stock_planeta) productoData[0]; // Extract Stock_planeta from array
            
            Producto producto = (Producto) productoData[1]; // Extract Producto from array

            // Create ProductoDTO object from retrieved data
            ProductoDTO productoDTO = new ProductoDTO(producto.getId(),producto.getNombre(),producto.getImagen(),producto.getVolumen(),stockPlaneta.getFactorDemanda(), stockPlaneta.getFactorOferta(),stockPlaneta.getStock());            // Set properties of productoDTO using data from stockPlaneta and producto
            // Add productoDTO to stockSinPrecios list
            stockSinPrecios.add(productoDTO);
        }
        return stockSinPrecios;
    }

    public ProductoDTO recuperarProductoXPlaneta(Long idPlaneta, Long idProducto)
    {
        List<Object[]> productoObtained=stockPlanetaRepository.findProductsByPlanetaIdAndProductoID(idPlaneta,idProducto);
        ProductoDTO productoDTO=new ProductoDTO();

        for (Object[] productoData : productoObtained) {
            Stock_planeta stockPlaneta = (Stock_planeta) productoData[0]; // Extract Stock_planeta from array
            
            Producto producto = (Producto) productoData[1]; // Extract Producto from array

            // Create ProductoDTO object from retrieved data
            productoDTO = new ProductoDTO(producto.getId(),producto.getNombre(),producto.getImagen(),producto.getVolumen(),stockPlaneta.getFactorDemanda(), stockPlaneta.getFactorOferta(),stockPlaneta.getStock(),stockPlaneta.getStock());            // Set properties of productoDTO using data from stockPlaneta and producto
            // Add productoDTO to stockSinPrecios list
        }
       
        return productoDTO;
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
