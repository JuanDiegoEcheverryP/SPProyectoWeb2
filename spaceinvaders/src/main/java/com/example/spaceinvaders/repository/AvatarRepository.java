package com.example.spaceinvaders.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.spaceinvaders.model.Avatar;
import com.example.spaceinvaders.model.Jugador;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    
    List<Avatar> findAllByNombre(String text);

    List<Avatar> findAllByNombreStartingWithIgnoreCase(String text);

    List<Avatar> findAllByNombreEndingWithIgnoreCase(String text);

    List<Avatar> findAllByNombreContainingIgnoreCase(String text);

    @Query("SELECT j FROM Jugador j WHERE j.avatar.id = :idAvatar")
    List<Jugador> findNavesByNaveId(@Param("idAvatar") Long idAvatar);
}
