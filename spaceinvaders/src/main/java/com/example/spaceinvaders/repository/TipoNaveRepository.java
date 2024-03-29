package com.example.spaceinvaders.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.spaceinvaders.model.Nave;
import com.example.spaceinvaders.model.TipoNave;


@Repository
public interface TipoNaveRepository  extends JpaRepository<TipoNave, Long> {

    List<TipoNave> findAllByNombre(String text);

    List<TipoNave> findAllByNombreStartingWithIgnoreCase(String text);

    List<TipoNave> findAllByNombreEndingWithIgnoreCase(String text);

    List<TipoNave> findAllByNombreContainingIgnoreCase(String text);

    @Query("SELECT n FROM Nave n WHERE n.tipoNave.id = :idTipoNave")
    List<Nave> findNavesByNaveId(@Param("idTipoNave") Long idTipoNave);
}

