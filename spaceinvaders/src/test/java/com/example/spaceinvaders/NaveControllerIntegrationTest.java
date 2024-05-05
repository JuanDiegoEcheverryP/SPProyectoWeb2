package com.example.spaceinvaders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

import org.apache.hc.core5.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.example.spaceinvaders.model.Avatar;
import com.example.spaceinvaders.model.Estrella;
import com.example.spaceinvaders.model.Jugador;
import com.example.spaceinvaders.model.Nave;
import com.example.spaceinvaders.model.Planeta;
import com.example.spaceinvaders.model.TipoNave;
import com.example.spaceinvaders.model.DTO.PatchRolNave;
import com.example.spaceinvaders.model.DTO.RegistroDTO;
import com.example.spaceinvaders.model.DTO.UsuarioDTO;
import com.example.spaceinvaders.repository.AvatarRepository;
import com.example.spaceinvaders.repository.EstrellaRepository;
import com.example.spaceinvaders.repository.JugadorRepository;
import com.example.spaceinvaders.repository.NaveRepository;
import com.example.spaceinvaders.repository.PlanetaRepository;
import com.example.spaceinvaders.repository.TipoNaveRepository;

import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@ActiveProfiles("integration-testing")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class NaveControllerIntegrationTest {
    private static final String SERVER_URL = "http://localhost:8081";

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
	
	private  Random rand = new Random();

	@BeforeEach
    void init() {
		//crear un avatar
		Avatar avatar=new Avatar("Bazinga", "");
		avatarRepository.save(avatar);

        //crear una estrella
		Estrella estrella=new Estrella("Esfera de Dyson", 1f, 1f, 1f);
		estrellaRepository.save(estrella);
		
		TipoNave tipo=new TipoNave("Prototipo", 10000f,   1f, "");
		tipoNaveRepository.save(tipo);

        Nave nave = new Nave("Estrella de la muerte",9876f,5000f,estrella, tipo);


	}

	@Autowired
    private TestRestTemplate rest;

}
