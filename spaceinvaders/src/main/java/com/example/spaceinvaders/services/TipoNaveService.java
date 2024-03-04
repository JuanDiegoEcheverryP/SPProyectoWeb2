package com.example.spaceinvaders.services;

import java.util.List;

import com.example.spaceinvaders.model.Estrella;
import com.example.spaceinvaders.model.TipoNave;
import com.example.spaceinvaders.repository.TipoNaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipoNaveService {
    @Autowired
    private TipoNaveRepository tipoNaveRepository;

    public List<TipoNave> listarTipoNaves() {
        return tipoNaveRepository.findAll();
    }

    public TipoNave recuperarTipoNave(Long id)
    {
        return tipoNaveRepository.findById(id).orElseThrow();
    }

    public TipoNave guardarTipoNave(TipoNave tipoNave)
    {

        return tipoNaveRepository.save(tipoNave);
    }

    public void borrarTipoNave(TipoNave tipoNave)
    {
        tipoNaveRepository.delete(tipoNave);
    }

    public List<TipoNave> buscarNombre(String textoBusqueda) {
        return tipoNaveRepository.findAllByNombre(textoBusqueda);
    }

    public List<TipoNave> buscarTipoNavesQueContengan(String textoBusqueda) {
        return tipoNaveRepository.findAllByNombreContainingIgnoreCase(textoBusqueda);
    }

    public List<TipoNave> buscarTipoNavesQueTerminenCon(String textoBusqueda) {
        return tipoNaveRepository.findAllByNombreEndingWithIgnoreCase(textoBusqueda);
    }

    public List<TipoNave> buscarTipoNavesQueEmpiecenCon(String textoBusqueda) {
        return tipoNaveRepository.findAllByNombreContainingIgnoreCase(textoBusqueda);
    }

    public String tipoNaveValidationNombre(TipoNave tipoNave)
    {
        String mensaje="";
        List<TipoNave> tipoNaveEvalNombre=tipoNaveRepository.findAllByNombre(tipoNave.getNombre());

       if(!tipoNaveEvalNombre.isEmpty() && tipoNaveEvalNombre.get(0).getId()!=tipoNave.getId())
        {
            mensaje="Ya existe un tipo de Nave con ese nombre";
        }

        return mensaje;
    }

    public TipoNave crearTipoNave(TipoNave tipoNave) {

        TipoNave nueva=tipoNaveRepository.save(tipoNave);
        return nueva;
    }
}
