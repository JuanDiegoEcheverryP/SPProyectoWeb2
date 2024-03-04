package com.example.spaceinvaders.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.spaceinvaders.model.Nave;


@Repository
public interface NaveRepository extends JpaRepository<Nave, Long> {
    List<Nave> findAllByNombre(String text);

    List<Nave> findAllByNombreStartingWithIgnoreCase(String text);

    List<Nave> findAllByNombreEndingWithIgnoreCase(String text);

    List<Nave> findAllByNombreContainingIgnoreCase(String text);
}
