package com.example.spaceinvaders.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spaceinvaders.model.Camino;
import com.example.spaceinvaders.model.Estrella;
import com.example.spaceinvaders.model.Planeta;
import com.example.spaceinvaders.model.DTO.EstrellaDTO;
import com.example.spaceinvaders.repository.CaminoRepository;

@Service
public class CaminoService {

    private  Random rand = new Random();

    @Autowired
    private CaminoRepository caminoRepository;
    Logger log = LoggerFactory.getLogger(getClass());

    public Boolean nuevoCaminoUnaEstrella(Estrella estrella) {
        Estrella estrella2=caminoRepository.findEstrellaByInicioId(rand.nextLong(caminoRepository.count()));

        Float distancia=calcularDistancia(estrella.getCoord_x(),estrella2.getCoord_x(),estrella.getCoord_y(),estrella2.getCoord_y(),estrella.getCoord_z(),estrella2.getCoord_z());
        Camino caminoNuevoIda= new Camino (estrella,estrella2,"nuevo",distancia);
        Camino caminoNuevoVenida= new Camino (estrella2,estrella,"nuevo",distancia);


        caminoNuevoIda=caminoRepository.save(caminoNuevoIda);
        caminoNuevoVenida=caminoRepository.save(caminoNuevoVenida);
        
        return true;
    }

    private float calcularDistancia(float x,float x2,float y,float y2,float z,float z2)
    {
        float distancia=0f;
    

        // Calcula las diferencias en cada dimensión
        double diferencia_x = x - x2;
        double diferencia_y = y - y2;
        double diferencia_z = z - z2;

        // Calcula la distancia euclidiana
        distancia = (float)Math.sqrt(diferencia_x * diferencia_x +
                                      diferencia_y * diferencia_y +
                                      diferencia_z * diferencia_z);

        return distancia;
    }

    public List<Camino> listarCaminos(Long id) {
        return caminoRepository.findCaminosPorEstrellaInicio(id);
    }

    public List<Estrella> obtenerEstrellaFinalPorEstrellaInicioId(Long id) {
        return caminoRepository.obtenerEstrellaFinalPorEstrellaInicioId(id);
    }

    public List<EstrellaDTO> obtenerEstrellasConectadasPorEstrellaInicioId(Long id) {
        List<Camino> a = caminoRepository.findCaminosPorEstrellaInicio(id);
        List<EstrellaDTO> estrellasFinales = new ArrayList<EstrellaDTO>();
        for (Camino camino : a) {
            boolean habitado = false;
            for (Planeta planeta : camino.getEstrellaFinal().getListaPlanetas()) {
                if (planeta.getHabitado()) {
                    habitado= true;
                }
            }
            EstrellaDTO nueva = new EstrellaDTO(camino.getEstrellaFinal().getId(), camino.getEstrellaFinal().getNombre(), camino.getDistancia(), habitado);
            estrellasFinales.add(nueva);
        }
        return estrellasFinales;
        
    }

    public Camino obtenerCaminoDosEstrellas(Long idInicio, Long idFinal) {
        System.out.println(idInicio);
        System.out.println(idFinal);
        List<Camino> a = caminoRepository.findCaminosPorEstrellaInicio(idInicio);
        for (Camino camino : a) {
            if (camino.getEstrellaFinal().getId() == idFinal) {
                return camino;
            }
        }
        return new Camino(null, null, null, null);
    }
}
