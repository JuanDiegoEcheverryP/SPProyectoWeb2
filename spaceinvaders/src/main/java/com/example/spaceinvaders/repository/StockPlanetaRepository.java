package com.example.spaceinvaders.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.spaceinvaders.model.Stock_planeta;


@Repository
public interface StockPlanetaRepository  extends JpaRepository<Stock_planeta, Long> {
    
    @Query("SELECT sp FROM Stock_planeta sp WHERE sp.planeta.nombre = :clave")
    List<Stock_planeta> findAllByPlanetaNombre(@Param("clave") String palabraClave);

    @Query("SELECT sp FROM Stock_planeta sp WHERE sp.producto.nombre = :clave")
    List<Stock_planeta> findAllByProductoNombre(@Param("clave") String palabraClave);

    //FUNCIONA
    @Query("SELECT sp FROM Stock_planeta sp WHERE sp.planeta.nombre LIKE %:clave%")
    List<Stock_planeta> findAllByPlanetaNombreContainingWith(@Param("clave") String palabraClave);
    @Query("SELECT sp FROM Stock_planeta sp WHERE sp.producto.nombre LIKE %:clave%")
    List<Stock_planeta> findAllByProductoNombreContainingWith(@Param("clave") String palabraClave);

    //FUNCIONA
    @Query("SELECT sp FROM Stock_planeta sp WHERE sp.planeta.nombre LIKE :clave%")
    List<Stock_planeta> findAllByPlanetaNombreStartingWith(@Param("clave") String palabraClave);
    @Query("SELECT sp FROM Stock_planeta sp WHERE sp.producto.nombre LIKE :clave%")
    List<Stock_planeta> findAllByProductoNombreStartingWith(@Param("clave") String palabraClave);

    
    @Query("SELECT sp FROM Stock_planeta sp WHERE sp.planeta.nombre LIKE %:clave")
    List<Stock_planeta> findAllByPlanetaNombreEndingWith(@Param("clave") String palabraClave);
    @Query("SELECT sp FROM Stock_planeta sp WHERE sp.producto.nombre LIKE %:clave")
    List<Stock_planeta> findAllByProductoNombreEndingWith(@Param("clave") String palabraClave);


    @Query("SELECT sp FROM Stock_planeta sp WHERE sp.planeta.id = :idPlaneta AND sp.producto.id = :idProducto")
    List<Stock_planeta> findExact(@Param("idPlaneta") Long idPlaneta, @Param("idProducto") Long idProducto);

    @Query("SELECT sp FROM Stock_planeta sp WHERE sp.planeta.id = :idPlaneta")
    List<Stock_planeta> findExactByPlanetas(@Param("idPlaneta") Long idPlaneta);

    List<Stock_planeta> findByPlanetaId(Long planetaId);
}
