package com.example.spaceinvaders.services;

import java.util.List;

import com.example.spaceinvaders.model.Avatar;
import com.example.spaceinvaders.model.Estrella;
import com.example.spaceinvaders.model.Jugador;
import com.example.spaceinvaders.model.Nave;
import com.example.spaceinvaders.model.Planeta;
import com.example.spaceinvaders.repository.AvatarRepository;
import com.example.spaceinvaders.repository.JugadorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AvatarService {
    @Autowired
    private AvatarRepository avatarRepository;

    @Autowired
    private JugadorRepository jugadorRepository;

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

    public String avatarValidationNombre(Avatar avatar)
    {
        String mensaje="";
        List<Avatar> avatarEvalNombre=avatarRepository.findAllByNombre(avatar.getNombre());

       if(!avatarEvalNombre.isEmpty() && avatarEvalNombre.get(0).getId()!=avatar.getId())
        {
            mensaje="Ya existe un avatar con ese nombre";
        }

        return mensaje;
    }

    public String validationAvatarBorrar(Avatar avatar)
    {
        //Falta validar que se pueda borrar
        String mensaje="";

        List<Jugador> jugadorEval = jugadorRepository.findJugadoresByAvatarId(avatar.getId());

        if(!jugadorEval.isEmpty()){
            mensaje= "Hay usuarios con ese avatar";
        }
        return mensaje;
    }

    public Avatar crearAvatar(Avatar avatar) {

        Avatar nueva=avatarRepository.save(avatar);
        return nueva;
    }
}
