package com.example.spaceinvaders.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.spaceinvaders.model.ProductoBodega;
import com.example.spaceinvaders.model.DTO.ProductoDTO;

import jakarta.transaction.Transactional;

@Repository
public interface ProductoBodegaRepository extends JpaRepository<ProductoBodega, Long> {

    @Query("SELECT n FROM ProductoBodega n WHERE n.nave.id = :idNave")
    List<ProductoBodega> findProductosBodegaByNaveId(@Param("idNave") Long idNave);
    
    List<ProductoBodega> findAllByProductoId(Long id);

    @Query("SELECT sp, p, pb FROM Stock_planeta sp " +
    "JOIN sp.producto p " +
    "JOIN p.listaPRoductoBodega pb " +
    "WHERE sp.planeta.id = :planetaId AND sp.producto.id = :productoId AND pb.nave.id = :naveId")
    List<Object[]> findProductByPlanetaIdAndProductoIDAndNaveID(Long planetaId, Long productoId, Long naveId);

    ProductoBodega findByNaveIdAndProductoId(Long naveId, Long productoId);

    @Transactional
    @Modifying
    @Query("UPDATE ProductoBodega pb SET pb.cantidad = :cantidad WHERE pb.nave.id = :naveId AND pb.producto.id = :productoId")
    int updateCantidad(@Param("naveId") Long naveId, @Param("productoId") Long productoId, @Param("cantidad") Integer cantidad);

    @Transactional
    @Modifying
    @Query("UPDATE ProductoBodega pb SET pb.vol = :vol WHERE pb.nave.id = :naveId AND pb.producto.id = :productoId")
    int updateVolumen(@Param("naveId") Long naveId, @Param("productoId") Long productoId, @Param("vol") Float vol);

    @Query("SELECT pb.cantidad FROM ProductoBodega pb WHERE pb.nave.id = :naveId AND pb.producto.id = :productoId")
    Integer findStockByNaveIdAndProductoId(@Param("naveId") Long naveId, @Param("productoId") Long productoId);

    @Query("SELECT n FROM ProductoBodega n WHERE n.nave.id = :idNave")
    List<ProductoBodega> findProductosByNnaveId(@Param("idNave") Long idNave);


}
