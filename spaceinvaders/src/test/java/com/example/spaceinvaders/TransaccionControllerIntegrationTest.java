package com.example.spaceinvaders;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.List;

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
import com.example.spaceinvaders.model.DTO.JugadorLogInDTO;
import com.example.spaceinvaders.model.DTO.PatchRolNave;
import com.example.spaceinvaders.model.DTO.ProductoDTO;
import com.example.spaceinvaders.model.DTO.RegistroDTO;
import com.example.spaceinvaders.model.DTO.RespuestaTransaccionDTO;
import com.example.spaceinvaders.model.DTO.UsuarioDTO;
import com.example.spaceinvaders.model.Enum.Rol;
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
import org.springframework.http.MediaType;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@ActiveProfiles("integration-testing")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class TransaccionControllerIntegrationTest {
    
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
	
    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void init() {
        Avatar avatar=new Avatar("alka", "");
		avatarRepository.save(avatar);
        //se probara compra y venta 
        //se crea una estrella
		Estrella estrella=new Estrella("Proxima centauri", 1f, 1f, 1f);
		estrellaRepository.save(estrella);

        //se crea un planeta
        Planeta planeta=new Planeta("planeta fugaz", true, "", estrella);
        planetaRepository.save(planeta);

        //crear dos productos
        Producto producto1=new Producto("pera", "", 10f);
        productoRepository.save(producto1);
        Producto producto2=new Producto("manzana", "", 20f);
        productoRepository.save(producto2);
        Producto producto3=new Producto("papaya", "", 50f);
        productoRepository.save(producto3);

        //ponerlos en el stock del planeta
        Stock_planeta stock1= new Stock_planeta(100L,20L,9,planeta,producto1);
        stockRepository.save(stock1);
        Stock_planeta stock2= new Stock_planeta(50L,200L,19,planeta,producto2);
        stockRepository.save(stock2);
        Stock_planeta stock3= new Stock_planeta(50000L,20L,19,planeta,producto3);
        stockRepository.save(stock3);

		//tipo nave
		TipoNave tipo=new TipoNave("rayo mcqueen", 500f,  100f, "");
		tipoNaveRepository.save(tipo);

		//crear 2 naves
		Nave nave1=new Nave("ponyo", 100f, 12f, estrella, tipo);
        //estas naves estaran en el mismo planeta habitado
        nave1.setLocalizacionPlaneta(planeta);
		Nave nave2=new Nave("kiki", 400f, 12f, estrella, tipo);
		nave2.setLocalizacionPlaneta(planeta);
        naveRepository.save(nave1);
		naveRepository.save(nave2);
		List<Nave> naves = naveRepository.findAll();

        //tendran en bodega un producto que tambi[en tiene el planeta
        //se guarda en el stock del planeta dos productos
        ProductoBodega bodega1= new ProductoBodega(9,10f*9,producto1,nave1);
        bodegaRepository.save(bodega1);
        ProductoBodega bodega2= new ProductoBodega(14,14*20f,producto2,nave2);
        bodegaRepository.save(bodega2);

        //se guarda un jugador de tipo capitan en cada nave 
        jugadorRepository.save(new Jugador("jugador1",passwordEncoder.encode("123"),Rol.capitan,naves.get(0),avatar));
        jugadorRepository.save(new Jugador("jugador2",passwordEncoder.encode("123"),Rol.capitan,naves.get(1),avatar));
    }

    //prueba 1: realizar una compra de un producto para una nave
    @Autowired
    private TestRestTemplate restTemplate;

    @SuppressWarnings("null")
    @Test
	void compraParaUnaNave() {
        List<Planeta> planetas = planetaRepository.findAll();

        List<Producto> productos = productoRepository.findAll();

        List<Jugador> jugadores = jugadorRepository.findAll();

        JugadorLogInDTO jugadorLogIn= new JugadorLogInDTO();
        jugadorLogIn.setNombre("jugador1");
        jugadorLogIn.setContrasena("123");
        //iniciar sesion para obtener el token
        UsuarioDTO usuarionuevo=rest.postForObject(SERVER_URL + "/api/jugador/login",jugadorLogIn, UsuarioDTO.class);

        		//esto se anade porque nos aparecio un problema despues de anadir el jwt
		//pero ahora todo funciona si enviamos el token respectivo
		restTemplate.getRestTemplate().setInterceptors(Collections.singletonList((request, body, execution) -> {
            HttpHeaders headers = request.getHeaders();
            headers.add("Authorization", "Bearer " + usuarionuevo.getToken());
            return execution.execute(request, body);
        }));

        // Crear el objeto de solicitud con el token JWT en el encabezado de autorización
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(usuarionuevo.getToken());			

        //se obtiene el producto del planeta que se va a comprar
        ProductoDTO producto = rest.exchange(
            SERVER_URL + "/api/stock/planeta/" + planetas.get(0).getId() + "/producto/" + productos.get(0).getId(),
            HttpMethod.GET,
            new HttpEntity<>(headers),
            ProductoDTO.class
        ).getBody();

        //revisar si el precio es el esperado 
        Float precioEsperado=100f/(1+9);
        System.out.println("PRECIOS: esperado "+ precioEsperado+" VS del back "+producto.getPrecioDemanda());
        assertEquals(precioEsperado,producto.getPrecioDemanda());
        
         //se calcula la informacion para enviar a la compra 
        int cantidadProducto=5;
        Float total=cantidadProducto*precioEsperado;
        System.out.println("TOTAL: "+total);
        CompraVentaDTO compra= new CompraVentaDTO(productos.get(0).getId(),cantidadProducto, planetas.get(0).getId(), jugadores.get(0).getNaveJuego().getId(), total);

        HttpEntity<CompraVentaDTO> requestEntity = new HttpEntity<>(compra, headers);
        //se realiza la compra
        // Realizar la solicitud PUT y obtener la respuesta
        ResponseEntity<RespuestaTransaccionDTO> response = rest.exchange(
            SERVER_URL + "/api/transaccion/compra",         
            HttpMethod.PUT,                                 
            requestEntity,         
            RespuestaTransaccionDTO.class
        );

        // Obtener el cuerpo de la respuesta
        RespuestaTransaccionDTO resCompra = response.getBody();
        assertEquals("Compra exitosa!",resCompra.getMensaje());
        
        
        //revisar los valores del repositorio
        Float creditoRestante=50f;
        System.out.println("Credito obtenido: "+naveRepository.findAll().get(0).getCredito());
        assertEquals(creditoRestante,naveRepository.findAll().get(0).getCredito());

        //valores esperados de compra
        Float bodegaCapacidad=140f;
        System.out.println("Bodega: "+naveRepository.sumVolByNaveId(jugadores.get(0).getNaveJuego().getId())); 
        assertEquals(bodegaCapacidad,naveRepository.sumVolByNaveId(jugadores.get(0).getNaveJuego().getId()));

        //respuesta esperada del repositorio
        int stock=4;
        assertEquals(stock,stockRepository.findStockByPlanetaIdAndProductoId(planetas.get(0).getId(), productos.get(0).getId()));

        int cantEnBodega=14;
        assertEquals(cantEnBodega,bodegaRepository.findByNaveIdAndProductoId(jugadores.get(0).getNaveJuego().getId(), productos.get(0).getId()).getCantidad());
    }
    
    //prueba 1.2: se realiza la compra pero la capacidad de la nave es insuficiente
    @Test
    void compraBodegaInsuficiente() {

        List<Planeta> planetas = planetaRepository.findAll();

        List<Producto> productos = productoRepository.findAll();

        List<Jugador> jugadores = jugadorRepository.findAll();

        JugadorLogInDTO jugadorLogIn= new JugadorLogInDTO();
        jugadorLogIn.setNombre("jugador1");
        jugadorLogIn.setContrasena("123");
        //iniciar sesion para obtener el token
        UsuarioDTO usuarionuevo=rest.postForObject(SERVER_URL + "/api/jugador/login",jugadorLogIn, UsuarioDTO.class);

        		//esto se anade porque nos aparecio un problema despues de anadir el jwt
		//pero ahora todo funciona si enviamos el token respectivo
		restTemplate.getRestTemplate().setInterceptors(Collections.singletonList((request, body, execution) -> {
            HttpHeaders headers = request.getHeaders();
            headers.add("Authorization", "Bearer " + usuarionuevo.getToken());
            return execution.execute(request, body);
        }));

        // Crear el objeto de solicitud con el token JWT en el encabezado de autorización
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(usuarionuevo.getToken());


        //se obtiene el producto del planeta que se va a comprar
        ProductoDTO producto = rest.exchange(
            SERVER_URL + "/api/stock/planeta/" + planetas.get(0).getId() + "/producto/" + productos.get(1).getId(),
            HttpMethod.GET,
            new HttpEntity<>(headers),
            ProductoDTO.class
        ).getBody();

        //revisar si el precio es el esperado 
        Float precioEsperado=50f/(1+19);
        System.out.println("PRECIOS: esperado "+ precioEsperado+" VS del back "+producto.getPrecioDemanda());
        assertEquals(precioEsperado,producto.getPrecioDemanda());
        
        //se calcula la informacion para enviar a la compra 
         //cantidad mayor a la disponible
        int cantidadProducto=22;
        Float total=cantidadProducto*precioEsperado;
        System.out.println("TOTAL: "+total);
        CompraVentaDTO compra= new CompraVentaDTO(productos.get(1).getId(),cantidadProducto, planetas.get(0).getId(), jugadores.get(0).getNaveJuego().getId(), total);

        HttpEntity<CompraVentaDTO> requestEntity = new HttpEntity<>(compra, headers);

        // Realizar la solicitud PUT y obtener la respuesta
        ResponseEntity<String> responseEntity = rest.exchange(
            SERVER_URL + "/api/transaccion/compra",         
            HttpMethod.PUT,                                 
            requestEntity,         
            String.class
        );


        // Obtener el cuerpo de la respuesta
        String error = (String) responseEntity.getBody();
        System.out.println("Error: " + error);
        assertEquals("No hay suficiente stock disponible",error);

        //revisar los valores del repositorio
        Float creditoRestante=100f;
        System.out.println("Credito obtenido: "+naveRepository.findAll().get(0).getCredito());
        assertEquals(creditoRestante,naveRepository.findAll().get(0).getCredito());

        //valores esperados de compra
        Float bodegaCapacidad=90f;
        System.out.println("Bodega: "+naveRepository.sumVolByNaveId(jugadores.get(0).getNaveJuego().getId())); 
        assertEquals(bodegaCapacidad,naveRepository.sumVolByNaveId(jugadores.get(0).getNaveJuego().getId()));

        //respuesta esperada del repositorio
        int stock=19;
        assertEquals(stock,stockRepository.findStockByPlanetaIdAndProductoId(planetas.get(0).getId(), productos.get(1).getId()));

    }

    //prueba 1.3: se realiza una compra pero los creditos son insuficientes
    @Test
    void compraCreditoInsuficiente() {
        List<Planeta> planetas = planetaRepository.findAll();

        List<Producto> productos = productoRepository.findAll();

        List<Jugador> jugadores = jugadorRepository.findAll();

        JugadorLogInDTO jugadorLogIn= new JugadorLogInDTO();
        jugadorLogIn.setNombre("jugador1");
        jugadorLogIn.setContrasena("123");
        //iniciar sesion para obtener el token
        UsuarioDTO usuarionuevo=rest.postForObject(SERVER_URL + "/api/jugador/login",jugadorLogIn, UsuarioDTO.class);

        		//esto se anade porque nos aparecio un problema despues de anadir el jwt
		//pero ahora todo funciona si enviamos el token respectivo
		restTemplate.getRestTemplate().setInterceptors(Collections.singletonList((request, body, execution) -> {
            HttpHeaders headers = request.getHeaders();
            headers.add("Authorization", "Bearer " + usuarionuevo.getToken());
            return execution.execute(request, body);
        }));

        // Crear el objeto de solicitud con el token JWT en el encabezado de autorización
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(usuarionuevo.getToken());


        //se obtiene el producto del planeta que se va a comprar
        ProductoDTO producto = rest.exchange(
            SERVER_URL + "/api/stock/planeta/" + planetas.get(0).getId() + "/producto/" + productos.get(2).getId(),
            HttpMethod.GET,
            new HttpEntity<>(headers),
            ProductoDTO.class
        ).getBody();

        //revisar si el precio es el esperado 
        Float precioEsperado=50000f/(1+19);
        System.out.println("PRECIOS: esperado "+ precioEsperado+" VS del back "+producto.getPrecioDemanda());
        assertEquals(precioEsperado,producto.getPrecioDemanda());
        
        //se calcula la informacion para enviar a la compra 
         //cantidad mayor a la disponible
        int cantidadProducto=19;
        Float total=cantidadProducto*precioEsperado;
        System.out.println("TOTAL: "+total);
        CompraVentaDTO compra= new CompraVentaDTO(productos.get(2).getId(),cantidadProducto, planetas.get(0).getId(), jugadores.get(0).getNaveJuego().getId(), total);

    //se realiza la compra
        // Realizar la solicitud PUT y obtener la respuesta
        ResponseEntity<String> responseEntity = rest.exchange(
            SERVER_URL + "/api/transaccion/compra",
            HttpMethod.PUT,
            new HttpEntity<>(compra, headers),
            String.class
        );

        // Obtener el cuerpo de la respuesta
        String error = (String) responseEntity.getBody();
        System.out.println("Error: " + error);
        assertEquals("La nave no tiene suficiente crédito",error);

        //revisar los valores del repositorio
        Float creditoRestante=100f;
        System.out.println("Credito obtenido: "+naveRepository.findAll().get(0).getCredito());
        assertEquals(creditoRestante,naveRepository.findAll().get(0).getCredito());

        //valores esperados de compra
        Float bodegaCapacidad=90f;
        System.out.println("Bodega: "+naveRepository.sumVolByNaveId(jugadores.get(0).getNaveJuego().getId())); 
        assertEquals(bodegaCapacidad,naveRepository.sumVolByNaveId(jugadores.get(0).getNaveJuego().getId()));

        //respuesta esperada del repositorio
        int stock=19;
        assertEquals(stock,stockRepository.findStockByPlanetaIdAndProductoId(planetas.get(0).getId(), productos.get(2).getId()));
    }
}
