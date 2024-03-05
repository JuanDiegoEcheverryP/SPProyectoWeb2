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
}
