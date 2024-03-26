package com.example.spaceinvaders.services;

import com.example.spaceinvaders.model.Producto;
import com.example.spaceinvaders.model.ProductoBodega;
import com.example.spaceinvaders.model.Stock_planeta;
import com.example.spaceinvaders.model.DTO.ProductoDTO;
import com.example.spaceinvaders.repository.ProductoBodegaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoBodegaService {

    @Autowired
    private ProductoBodegaRepository productoBodegaRepository;

    public List<ProductoBodega> obtenerTodasLasProductoBodegas() {
        return productoBodegaRepository.findAll();
    }

    public List<ProductoBodega> buscarBodegasConProducto(Producto producto)
    {
        return productoBodegaRepository.findAllByProductoId(producto.getId());
    }

    public ProductoDTO recuperarProductoXBodega(Long idPlaneta, Long idProducto, Long idBodega)
    {
        List<Object[]> productoObtained=productoBodegaRepository.findProductByPlanetaIdAndProductoID(idPlaneta,idProducto,idBodega);
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
    
}
