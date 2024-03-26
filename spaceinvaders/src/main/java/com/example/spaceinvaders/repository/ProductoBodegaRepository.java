package com.example.spaceinvaders.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.spaceinvaders.model.ProductoBodega;

@Repository
public interface ProductoBodegaRepository extends JpaRepository<ProductoBodega, Long> {

    @Query("SELECT n FROM ProductoBodega n WHERE n.nave.id = :idNave")
    List<ProductoBodega> findProductosBodegaByNaveId(@Param("idNave") Long idNave);
    
    List<ProductoBodega> findAllByProductoId(Long id);

    @Query("SELECT sp, p, pb FROM Stock_planeta sp " +
    "JOIN sp.producto p " +
    "JOIN p.listaPRoductoBodega pb " +
    "WHERE sp.planeta.id = :planetaId AND sp.producto.id = :productoId AND pb.id = :bodegaId")
    List<Object[]> findProductByPlanetaIdAndProductoID(Long planetaId, Long productoId, Long bodegaId);

}
