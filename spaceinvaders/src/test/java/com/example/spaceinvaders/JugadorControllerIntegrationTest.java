package com.example.spaceinvaders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.hc.core5.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.example.spaceinvaders.model.Avatar;
import com.example.spaceinvaders.model.Estrella;
import com.example.spaceinvaders.model.Jugador;
import com.example.spaceinvaders.model.Nave;
import com.example.spaceinvaders.model.Planeta;
import com.example.spaceinvaders.model.TipoNave;
import com.example.spaceinvaders.model.DTO.JugadorLogInDTO;
import com.example.spaceinvaders.model.DTO.PatchRolNave;
import com.example.spaceinvaders.model.DTO.RegistroDTO;
import com.example.spaceinvaders.model.DTO.UsuarioDTO;
import com.example.spaceinvaders.model.Enum.Rol;
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
class JugadorControllerIntegrationTest {

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

	@Autowired
    private PasswordEncoder passwordEncoder;

	@Autowired
    private TestRestTemplate restTemplate;
	
	private  Random rand = new Random();

	@BeforeEach
    void init() {
		//crear un avatar
		Avatar avatar=new Avatar("alka", "");
		avatarRepository.save(avatar);

		//crear una estrella
		Estrella estrella=new Estrella("Proxima centauri", 1f, 1f, 1f);
		estrellaRepository.save(estrella);
		
		TipoNave tipo=new TipoNave("rayo mcqueen", 100f,  100f, "");
		tipoNaveRepository.save(tipo);

		//crear 2 naves
		Nave nave1=new Nave("ponyo", 40f, 12f, null, tipo);
		Nave nave2=new Nave("kiki", 40f, 12f, null, tipo);
		naveRepository.save(nave1);
		naveRepository.save(nave2);
		List<Nave> naves = naveRepository.findAll();
		
		//crear 32 jugadores
		String[] nombreJugadorPt1 = {"Cedric","Sorcha","Pacorro","Riobard"};
        String[] nombreJugadorPt2 = {"Turban","Float","Yateman","Tibols","Matthis","Elcy","Maidlow","Lamburne"};

		Rol[] rol= {Rol.piloto,Rol.comerciante};

		int contadorJugadores=0;
		
		for(String nombre1:nombreJugadorPt1 )
        {
            for(String nombre2:nombreJugadorPt2)
            {
                String nombreJugador=nombre1+" "+nombre2;
                String contrasena="12345";

                //poner capitanes
                if(contadorJugadores<2)
                {
                    jugadorRepository.save(new Jugador(nombreJugador,passwordEncoder.encode(contrasena),Rol.capitan,naves.get(0),avatar));
                }
                //poner comerciantes
                else if(contadorJugadores<20)
                {
                    jugadorRepository.save(new Jugador(nombreJugador,passwordEncoder.encode(contrasena),Rol.comerciante,naves.get(0),avatar));
                }
				//Poner pilotos
                else
                {
                    jugadorRepository.save(new Jugador(nombreJugador,passwordEncoder.encode(contrasena),Rol.piloto,naves.get(0),avatar));
                }

                contadorJugadores++;
            }
        }

		jugadorRepository.save(new Jugador("jugador1",passwordEncoder.encode("123"),Rol.capitan,naves.get(0),avatar));
	}

	@Autowired
    private TestRestTemplate rest;

	//PATCH
	@Test
	void anadirJugadorEnNaveDisponible() {
		JugadorLogInDTO jugadorLogIn= new JugadorLogInDTO();
        jugadorLogIn.setNombre("jugador1");
        jugadorLogIn.setContrasena("123");

        //iniciar sesion para obtener el token
        UsuarioDTO usuarionuevo1=rest.postForObject(SERVER_URL + "/api/jugador/login",jugadorLogIn, UsuarioDTO.class);

		restTemplate.getRestTemplate().setInterceptors(Collections.singletonList((request, body, execution) -> {
            HttpHeaders headers = request.getHeaders();
            headers.add("Authorization", "Bearer " + usuarionuevo1.getToken());
            return execution.execute(request, body);
        }));

        // Crear el objeto de solicitud con el token JWT en el encabezado de autorización
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(usuarionuevo1.getToken());	

		List<Avatar> avatares = avatarRepository.findAll();
		List<Nave> naves = naveRepository.findAll();
		UsuarioDTO usuarionuevo=rest.postForObject(SERVER_URL + "/api/jugador/registro",new RegistroDTO("jugador2", "123", null, null, avatares.get(0), "123"), UsuarioDTO.class);
		
		UsuarioDTO usuarioActualizado=rest.patchForObject(SERVER_URL +"/api/jugador/"+ usuarionuevo.getId()+"/rol/nave",new PatchRolNave(Rol.piloto,naves.get(0).getId()) ,UsuarioDTO.class);
		System.out.println(usuarioActualizado.toString());

		UsuarioDTO usuarioActualizadoGET =rest.getForObject(SERVER_URL +"/api/jugador/"+ usuarionuevo.getId(), UsuarioDTO.class);
		System.out.println("actualizado "+usuarioActualizadoGET.toString());
        
		UsuarioDTO usuarioEsperado = new UsuarioDTO(usuarioActualizado.getId(),"jugador2", Rol.piloto,"",naves.get(0).getId());
        System.out.println("esperado "+usuarioEsperado.toString());

		assertEquals(usuarioEsperado,usuarioActualizadoGET);
	}

	//GET
	@Test
	void comprobarLoginUsuarios() {
		String[] nombreJugadorPt1 = {"Cedric","Sorcha","Pacorro","Riobard"};
        String[] nombreJugadorPt2 = {"Turban","Float","Yateman","Tibols","Matthis","Elcy","Maidlow","Lamburne"};

		Rol[] rol= {Rol.piloto,Rol.comerciante};

		Integer contadorJugadores = 0;

		for(String nombre1:nombreJugadorPt1 )
        {
            for(String nombre2:nombreJugadorPt2)
            {
                String nombreJugador=nombre1+" "+nombre2;
                String contrasena="12345";

				JugadorLogInDTO jugadorLogIn= new JugadorLogInDTO();
				jugadorLogIn.setNombre(nombreJugador);
				jugadorLogIn.setContrasena(contrasena);

				//iniciar sesion para obtener el token
				UsuarioDTO usuarionuevo=rest.postForObject(SERVER_URL + "/api/jugador/login",jugadorLogIn, UsuarioDTO.class);

				restTemplate.getRestTemplate().setInterceptors(Collections.singletonList((request, body, execution) -> {
					HttpHeaders headers = request.getHeaders();
					headers.add("Authorization", "Bearer " + usuarionuevo.getToken());
					return execution.execute(request, body);
				}));

				// Crear el objeto de solicitud con el token JWT en el encabezado de autorización
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				headers.setBearerAuth(usuarionuevo.getToken());	


				assertEquals(usuarionuevo.getNombre(), nombreJugador);
				assertEquals(usuarionuevo.getId(), contadorJugadores + 1);

                if(contadorJugadores<2)
                {
                    assertEquals(usuarionuevo.getRol(), Rol.capitan);
                }
                //Comerciantes
                else if(contadorJugadores<20)
				{
					assertEquals(usuarionuevo.getRol(), Rol.comerciante);
                }
				//Pilotos
                else
                {
					assertEquals(usuarionuevo.getRol(), Rol.piloto);
                }
                contadorJugadores++;
            }
        }
	}

	@Test
	void verJugadorInexistente() {
		JugadorLogInDTO jugadorLogIn= new JugadorLogInDTO();
        jugadorLogIn.setNombre("jugador1");
        jugadorLogIn.setContrasena("123");

        //iniciar sesion para obtener el token
        UsuarioDTO usuarionuevo=rest.postForObject(SERVER_URL + "/api/jugador/login",jugadorLogIn, UsuarioDTO.class);

		restTemplate.getRestTemplate().setInterceptors(Collections.singletonList((request, body, execution) -> {
            HttpHeaders headers = request.getHeaders();
            headers.add("Authorization", "Bearer " + usuarionuevo.getToken());
            return execution.execute(request, body);
        }));

        // Crear el objeto de solicitud con el token JWT en el encabezado de autorización
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(usuarionuevo.getToken());		

		ResponseEntity<String> respuesta = rest.getForEntity(SERVER_URL + "/api/jugador/999", String.class);
		assertEquals("No existe un jugador con ese id", respuesta.getBody());
	}
}
