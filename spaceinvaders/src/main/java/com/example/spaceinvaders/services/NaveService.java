package com.example.spaceinvaders.services;

import com.example.spaceinvaders.model.Estrella;
import com.example.spaceinvaders.model.Jugador;
import com.example.spaceinvaders.model.Nave;
import com.example.spaceinvaders.model.ProductoBodega;
import com.example.spaceinvaders.model.DTO.TripulacionDTO;
import com.example.spaceinvaders.model.Planeta;
import com.example.spaceinvaders.repository.JugadorRepository;
import com.example.spaceinvaders.repository.NaveRepository;
import com.example.spaceinvaders.repository.ProductoBodegaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NaveService {

    @Autowired
    private NaveRepository naveRepository;

    @Autowired
    private JugadorRepository jugadorRepository;

    @Autowired
    private ProductoBodegaRepository bodegaRepository;

    public boolean validarCreditoNave(Long idNave,Float total)
    {
        if(0<naveRepository.findCreditoById(idNave)-total)
            return true;

        return false;
    }

    public boolean validarCapacidadBodega(Long idNave,Long idProducto,int cant)
    {
        //pedir la suma de toda la bodega de la nave 
        Float volActual=naveRepository.sumVolByNaveId(idNave);
        //pedir el volumen del producto multiplicarlo por la cantidad 
        Float volTotalProducto=cant*naveRepository.findVolumenByProductoId(idProducto);
        //obtener la capacidad de la nave
        Float capacidad=naveRepository.findCapacidadBodegaByNaveId(idNave);

        if(volActual+volTotalProducto<=capacidad)
            return true;

        return false;
    }

    public void actualizarCreditos(Long idNave,Float total)
    {
        naveRepository.sumarCreditoNave(idNave, total);
    }

    public Float obtenerVolumenTotal(Long idNave)
    {
        return naveRepository.sumVolByNaveId(idNave);
    }

    public List<Nave> listaNaves() {
        return naveRepository.findAll();
    }

    public Nave recuperarNave(Long id)
    {
        return naveRepository.findById(id).orElseThrow();
    }

    public Nave guardarNave(Nave nave)
    {
        return naveRepository.save(nave);
    }

    public void borrarNave(Nave nave)
    {
        naveRepository.delete(nave);
    }

    public List<Nave> buscarNombre(String textoBusqueda) {
        return naveRepository.findAllByNombre(textoBusqueda);
    }

    public List<Nave> buscarNavesQueContengan(String textoBusqueda) {
        return naveRepository.findAllByNombreStartingWithIgnoreCase(textoBusqueda);
    }

    public List<Nave> buscarNavesQueTerminenCon(String textoBusqueda) {
        return naveRepository.findAllByNombreEndingWithIgnoreCase(textoBusqueda);
    }

    public List<Nave> buscarNavesQueEmpiecenCon(String textoBusqueda) {
        return naveRepository.findAllByNombreContainingIgnoreCase(textoBusqueda);
    }

    public String naveValidationNombre(Nave nave)
    {
        String mensaje="";
        List<Nave> naveEvalNombre=naveRepository.findAllByNombre(nave.getNombre());

        if(!naveEvalNombre.isEmpty() && naveEvalNombre.get(0).getId()!=nave.getId())
        {
            mensaje="Ya existe una nave con ese nombre";
        }

        return mensaje;
    }

    public String naveValidarBorrar(Nave nave) {
        String mensaje="";

        List<Jugador> jugadorEval = jugadorRepository.findJugadoresByNaveId(nave.getId());
        List<ProductoBodega> bodegaEval = bodegaRepository.findProductosBodegaByNaveId(nave.getId());
        if(!jugadorEval.isEmpty()){
            return "Hay usuarios en esa nave";
        }
        else if (!bodegaEval.isEmpty()) {
            return "Hay productos en esa nave";
        }


        return mensaje;
    }

    public Nave crearNave(Nave nave) {
        Nave nueva=naveRepository.save(nave);
        return nueva;
    }

    public Planeta obtenerPlanetaPorNaveId(Long id) {
        return naveRepository.findPlanetByNaveId(id);
    }

    public Estrella obtenerEstrellaPorNaveId(Long id) {
        return naveRepository.findEstrellaByNaveId(id);
    }

    public List<TripulacionDTO> obtenerInfotripulaciones() {
       
        List<TripulacionDTO> tripulaciones = new ArrayList<>();

        // Obtener las listas de capitanes y cantidad de tripulantes por nave
        List<Object[]> capitanes = naveRepository.findCapitanesByNave();
        List<Object[]> cant = naveRepository.countJugadoresByNave();

        // Iterar sobre cada nave
        for (Object[] capitan : capitanes) {
            Long idNave = (Long) capitan[0]; // Obtener el ID de la nave
            Jugador jugador = (Jugador) capitan[1]; // Obtener el capitán

            // Buscar la cantidad de tripulantes de esta nave
            Integer cantidadTripulantes = 0;
            for (Object[] c : cant) {
                Long idNaveCant = (Long) c[0];
                if (idNave.equals(idNaveCant)) {
                    cantidadTripulantes = ((Number) c[1]).intValue();
                    break;
                }
            }

            // Crear un objeto TripulacionDTO y agregarlo a la lista de tripulaciones
            TripulacionDTO tripulacionDTO = new TripulacionDTO();
            tripulacionDTO.setNave(naveRepository.findById(idNave).orElse(null));
            tripulacionDTO.setCapitan(jugador.getNombre());
            tripulacionDTO.setCantidadTripulantes(cantidadTripulantes);
            tripulaciones.add(tripulacionDTO);
        }

        return tripulaciones;
    }
    

}
