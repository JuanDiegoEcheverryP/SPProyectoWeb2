package com.example.spaceinvaders.services;

import com.example.spaceinvaders.model.Nave;
import com.example.spaceinvaders.model.Producto;
import com.example.spaceinvaders.model.ProductoBodega;
import com.example.spaceinvaders.model.Stock_planeta;
import com.example.spaceinvaders.model.DTO.ProductoDTO;
import com.example.spaceinvaders.model.DTO.productoBodegaDTO;
import com.example.spaceinvaders.repository.NaveRepository;
import com.example.spaceinvaders.repository.ProductoBodegaRepository;
import com.example.spaceinvaders.repository.ProductoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoBodegaService {

    @Autowired
    private ProductoBodegaRepository productoBodegaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private NaveRepository naveRepository;


    public List<ProductoBodega> obtenerTodasLasProductoBodegas() {
        return productoBodegaRepository.findAll();
    }

    public List<ProductoBodega> buscarBodegasConProducto(Producto producto)
    {
        
        return productoBodegaRepository.findAllByProductoId(producto.getId());
    }

    public boolean validarCantidadVenta(Long idProducto,Long idNave, int cant) {
        
        if(cant<=productoBodegaRepository.findStockByNaveIdAndProductoId(idNave, idProducto))
            return true;

        return false;
        
    }

    public ProductoDTO recuperarProductoXBodega(Long idPlaneta, Long idProducto, Long idNave)
    {
        List<Object[]> productoObtained=productoBodegaRepository.findProductByPlanetaIdAndProductoIDAndNaveID(idPlaneta,idProducto,idNave);
        ProductoDTO productoDTO=new ProductoDTO();

        for (Object[] productoData : productoObtained) {
            Stock_planeta stockPlaneta = (Stock_planeta) productoData[0]; // Extract Stock_planeta from array
            
            Producto producto = (Producto) productoData[1]; // Extract Producto from array

            ProductoBodega productoBodega = (ProductoBodega) productoData[2]; // Extract Stock_planeta from array

            // Create ProductoDTO object from retrieved data
            productoDTO = new ProductoDTO(producto.getId(),producto.getNombre(),producto.getImagen(),producto.getVolumen(),stockPlaneta.getFactorDemanda(), stockPlaneta.getFactorOferta(),stockPlaneta.getStock(),productoBodega.getCantidad());            // Set properties of productoDTO using data from stockPlaneta and producto
            // Add productoDTO to stockSinPrecios list
        }
        return productoDTO;
    }

    public void actualizarBodega(Long idNave,Long idProducto, Integer cantidadProducto, int tipoTransaccion)
    {
        ProductoBodega productoBodega = productoBodegaRepository.findByNaveIdAndProductoId(idNave, idProducto);

        if(tipoTransaccion==1)//es compra
        {
                    // Verificar si el producto ya existe en la bodega
            
            if (productoBodega != null) {
                // El producto ya existe en la bodega, actualizar la cantidad
                int nuevaCantidad=productoBodega.getCantidad() + cantidadProducto;
                Float nuevoVol=(productoBodega.getVol()/productoBodega.getCantidad())*nuevaCantidad;
                

                productoBodegaRepository.updateCantidad(idNave, idProducto, nuevaCantidad);
                productoBodegaRepository.updateVolumen(idNave, idProducto, nuevoVol);

            } else {
                // El producto no existe en la bodega, crear una nueva entrada
                Producto producto = productoRepository.findById(idProducto)
                                        .orElseThrow(() -> new RuntimeException("No se encontró el producto con ID: " + idProducto));
                Nave nave = naveRepository.findById(idNave)
                                .orElseThrow(() -> new RuntimeException("No se encontró la nave con ID: " + idNave));
                
                ProductoBodega nuevaBodega = new ProductoBodega();
                nuevaBodega.setProducto(producto);
                nuevaBodega.setNave(nave);
                nuevaBodega.setCantidad(cantidadProducto);
                nuevaBodega.setVol(producto.getVolumen()); // Aquí debes proporcionar el volumen del producto

                productoBodegaRepository.save(nuevaBodega);
            }

        }
        else //es una venta
        {
            //solo es restar del volumen y la cantidad
            int nuevaCantidad=productoBodega.getCantidad() - cantidadProducto;

            if(nuevaCantidad==0)
            {
                productoBodegaRepository.delete(productoBodega);
            }
            else
            {
                Float nuevoVol=(productoBodega.getVol()/productoBodega.getCantidad())*nuevaCantidad;
            
                productoBodegaRepository.updateCantidad(idNave, idProducto, nuevaCantidad);
                productoBodegaRepository.updateVolumen(idNave, idProducto, nuevoVol);
            }
        }
    }

    public List<ProductoBodega> buscarProductosPorBodega(Long id) {
        return productoBodegaRepository.findProductosByNnaveId(id); 
    }

    public List<productoBodegaDTO> buscarProductosDTOPorBodega(Long id) {
        List<ProductoBodega> a =productoBodegaRepository.findProductosByNnaveId(id);
        List<productoBodegaDTO> f = new ArrayList<>();
        for (ProductoBodega productoBodega : a) {
            Producto temp = productoBodega.getProducto();
            productoBodegaDTO insertar = new productoBodegaDTO(temp.getId(),temp.getNombre(),productoBodega.getCantidad(),productoBodega.getVol(),temp.getImagen());
            f.add(insertar);
        }
        return f; 
    }

    public boolean obtenerProductoBodega(Long idNave, Long idProducto) {
        List<ProductoBodega> a =productoBodegaRepository.findProductosByNnaveId(idNave);
        for (ProductoBodega producto : a) {
            System.out.println(producto.getProducto().getId());
            if(producto.getProducto().getId() == idProducto) {
                return true;
            }
        }
        return false;
    }
    
    
}
