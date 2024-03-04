package com.example.spaceinvaders.services;

import java.util.List;

import com.example.spaceinvaders.model.Avatar;
import com.example.spaceinvaders.model.Avatar;
import com.example.spaceinvaders.repository.AvatarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AvatarService {
    @Autowired
    private AvatarRepository avatarRepository;

    public List<Avatar> listarAvatars() {
        return avatarRepository.findAll();
    }

    public Avatar recuperarAvatar(Long id)
    {
        return avatarRepository.findById(id).orElseThrow();
    }

    public Avatar guardarAvatar(Avatar avatar)
    {
        return avatarRepository.save(avatar);
    }

    public void borrarAvatar(Avatar avatar)
    {
        avatarRepository.delete(avatar);
    }

    public List<Avatar> buscarNombre(String textoBusqueda) {
        return avatarRepository.findAllByNombre(textoBusqueda);
    }

    public List<Avatar> buscarAvatarsQueContengan(String textoBusqueda) {
        return avatarRepository.findAllByNombreStartingWithIgnoreCase(textoBusqueda);
    }

    public List<Avatar> buscarAvatarsQueTerminenCon(String textoBusqueda) {
        return avatarRepository.findAllByNombreEndingWithIgnoreCase(textoBusqueda);
    }

    public List<Avatar> buscarAvatarsQueEmpiecenCon(String textoBusqueda) {
        return avatarRepository.findAllByNombreContainingIgnoreCase(textoBusqueda);
    }

}
