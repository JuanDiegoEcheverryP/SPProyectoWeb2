package com.example.spaceinvaders.services;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spaceinvaders.model.Producto;
import com.example.spaceinvaders.model.ProductoBodega;
import com.example.spaceinvaders.repository.ProductoBodegaRepository;
import com.example.spaceinvaders.repository.ProductoRepository;

@Service
public class ProductoService {

    Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoBodegaService productoBodegaService;

    public List<Producto> listaProductos()
    {
        return productoRepository.findAll();
    }

    public Producto recuperarProducto(Long id)
    {
        return productoRepository.findById(id).orElseThrow();
    }

    public Producto guardarProducto(Producto producto)
    {
        return productoRepository.save(producto);
    }

    public void borrarProducto(Producto producto)
    {
        productoRepository.delete(producto);
    }

    public List<Producto> buscarNombre(String textoBusqueda) {
        return productoRepository.findAllByNombre(textoBusqueda);
    }

    public List<Producto> buscarProductosQueContengan(String textoBusqueda) {
        return productoRepository.findAllByNombreStartingWithIgnoreCase(textoBusqueda);
    }

    public List<Producto> buscarProductosQueTerminenCon(String textoBusqueda) {
        return productoRepository.findAllByNombreEndingWithIgnoreCase(textoBusqueda);
    }

    public List<Producto> buscarProductosQueEmpiecenCon(String textoBusqueda) {
        return productoRepository.findAllByNombreContainingIgnoreCase(textoBusqueda);
    }

    public String productoValidationNombre(Producto producto)
    {
        String mensaje="";
        List<Producto> productoEvalNombre=productoRepository.findAllByNombre(producto.getNombre());

       if(!productoEvalNombre.isEmpty() && productoEvalNombre.get(0).getId()!=producto.getId())
        {
            mensaje="Ya existe una producto con ese nombre";
        }

        return mensaje;
    }

    public String productoValidation(Producto producto)
    {
        
        String mensaje="";
        //quiero evaluar si el producto pertenece a una bodega
        //si pertenece no se puede borrar el producto

        List<ProductoBodega> bodegasQueContienen=productoBodegaService.buscarBodegasConProducto(producto);

        if(!bodegasQueContienen.isEmpty())
        {
            mensaje="No se puede borrar el producto porque pertenece por lo menos a una bodega de una nave";
        }

        return mensaje;
    }

    public Producto crearProducto(Producto producto) {

        Producto nueva=productoRepository.save(producto);

        return nueva;
    }
    
}
