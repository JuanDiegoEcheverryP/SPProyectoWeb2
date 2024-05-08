package com.example.spaceinvaders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.example.spaceinvaders.model.Avatar;
import com.example.spaceinvaders.model.Estrella;
import com.example.spaceinvaders.model.Jugador;
import com.example.spaceinvaders.model.Nave;
import com.example.spaceinvaders.model.Planeta;
import com.example.spaceinvaders.model.Producto;
import com.example.spaceinvaders.model.ProductoBodega;
import com.example.spaceinvaders.model.Stock_planeta;
import com.example.spaceinvaders.model.TipoNave;
import com.example.spaceinvaders.model.DTO.CompraVentaDTO;
import com.example.spaceinvaders.model.DTO.NaveDTO;
import com.example.spaceinvaders.model.DTO.ProductoDTO;
import com.example.spaceinvaders.model.DTO.RegistroDTO;
import com.example.spaceinvaders.model.DTO.RespuestaTransaccionDTO;
import com.example.spaceinvaders.model.DTO.UsuarioDTO;
import com.example.spaceinvaders.repository.AvatarRepository;
import com.example.spaceinvaders.repository.EstrellaRepository;
import com.example.spaceinvaders.repository.JugadorRepository;
import com.example.spaceinvaders.repository.NaveRepository;
import com.example.spaceinvaders.repository.PlanetaRepository;
import com.example.spaceinvaders.repository.ProductoBodegaRepository;
import com.example.spaceinvaders.repository.ProductoRepository;
import com.example.spaceinvaders.repository.StockPlanetaRepository;
import com.example.spaceinvaders.repository.TipoNaveRepository;
import org.springframework.http.HttpMethod;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@ActiveProfiles("integration-testing")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class NaveControllerIntegrationTest {
    
    private static final String SERVER_URL = "http://localhost:8081";
	
	@Autowired
    private TestRestTemplate rest;

    @Autowired
	private JugadorRepository jugadorRepository;
	
	@Autowired
	private EstrellaRepository estrellaRepository;
	
	@Autowired
	private AvatarRepository avatarRepository;
	
	@Autowired
	private NaveRepository naveRepository;

	@Autowired
	private TipoNaveRepository tipoNaveRepository;

    @Autowired
	private PlanetaRepository planetaRepository;

    @Autowired
	private ProductoRepository productoRepository;

    @Autowired
	private ProductoBodegaRepository bodegaRepository;

    @Autowired
	private StockPlanetaRepository stockRepository;
	
    @BeforeEach
    void init() {

        		//crear un avatar
		Avatar avatar=new Avatar("Bazinga", "");
		avatarRepository.save(avatar);

        //crear una estrella
		Estrella estrella=new Estrella("Esfera de Dyson", 1f, 1f, 1f);
		estrellaRepository.save(estrella);

		List<Estrella> estrellas = estrellaRepository.findAll();
		Planeta planeta = new Planeta("Kepler", true,"", estrellas.get(0));
		planetaRepository.save(planeta);
		
		TipoNave tipo=new TipoNave("Prototipo", 10000f,   1f, "");
		tipoNaveRepository.save(tipo);

        Nave nave = new Nave("Estrella de la muerte",9876f,5000f,estrella, tipo);
		naveRepository.save(nave);

    }

    @SuppressWarnings("null")
    @Test
    void actualizarNave() {
        Long idNave = naveRepository.findAll().get(0).getId();
        Long idEstrella = estrellaRepository.findAll().get(0).getId();
        Long idPlaneta = planetaRepository.findAll().get(0).getId();

        String url = SERVER_URL + "/api/nave/actualizarBien/" + idNave + "/" + idEstrella + "/" + idPlaneta;
        HttpEntity<Void> requestEntity = new HttpEntity<>(null);

        ResponseEntity<Boolean> response = rest.exchange(url, HttpMethod.PUT, requestEntity, Boolean.class);

		//La respuesta devuelve True si se pudo actualizar la nave
        assertTrue(response.getBody());

		//Comprobamos nuevamente que se halla realizado la actualizacion
		NaveDTO nav = rest.getForObject(SERVER_URL + "/api/nave/ver/1", NaveDTO.class);
		assertEquals(idEstrella, nav.getIdEstrella());
		assertEquals(idPlaneta, nav.getIdPlaneta());
    }

}
