package com.example.spaceinvaders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TimeoutException;
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
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@ActiveProfiles("system-test")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class systemTest {
    //Selenium
    String baseUrl;
    private ChromeDriver driver;
    private WebDriverWait wait;
	
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
        Avatar avatar=new Avatar("alka", "avatar1");
		avatarRepository.save(avatar);
        //se probara compra y venta 
        //se crea una estrella
		Estrella estrella=new Estrella("Proxima centauri", 1f, 1f, 1f);
		estrellaRepository.save(estrella);

        //se crea un planeta
        Planeta planeta=new Planeta("planeta fugaz", true, "", estrella);
        planetaRepository.save(planeta);

        //crear dos productos
        Producto producto1=new Producto("pera", "../../../assets/productos/producto2.png", 10f);
        productoRepository.save(producto1);
        Producto producto2=new Producto("manzana", "../../../assets/productos/producto3.png", 20f);
        productoRepository.save(producto2);
        Producto producto3=new Producto("papaya", "../../../assets/productos/producto4.png", 50f);
        productoRepository.save(producto3);

        //ponerlos en el stock del planeta
        Stock_planeta stock1= new Stock_planeta(100L,20L,9,planeta,producto1);
        stockRepository.save(stock1);
        Stock_planeta stock2= new Stock_planeta(50L,200L,19,planeta,producto2);
        stockRepository.save(stock2);
        Stock_planeta stock3= new Stock_planeta(50000L,20L,19,planeta,producto3);
        stockRepository.save(stock3);

		//tipo nave
		TipoNave tipo=new TipoNave("rayo mcqueen", 5000f,  100f, "");
		tipoNaveRepository.save(tipo);

		//crear 2 naves
		Nave nave1=new Nave("ponyo", 10000f, 1200f, estrella, tipo);
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
        jugadorRepository.save(new Jugador("jugador1","123",Rol.capitan,naves.get(0),avatar));
        jugadorRepository.save(new Jugador("jugador2","123",Rol.capitan,naves.get(1),avatar));

        
        //Selenium
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox"); // Bypass OS security model, MUST BE THE VERY FIRST OPTION
        // options.addArguments("--headless"); // To hide Chrome window
        options.addArguments("--disable-gpu"); // applicable to windows os only
        options.addArguments("--disable-extensions"); // disabling extensions
        options.addArguments("start-maximized"); // open Browser in maximized mode

        this.driver = new ChromeDriver(options);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        
        this.baseUrl = "http://localhost:4200";
    }

    @AfterEach
    void end() {
        this.driver.quit();
    }

    //Selenium
    /*
    @Test
    void selenium() throws InterruptedException {
        driver.get(baseUrl + "");
        try {
            //Pantalla principal
            WebElement btnRegistrarse = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("iniciarSesion")));
            wait.until(ExpectedConditions.elementToBeClickable(btnRegistrarse));
            btnRegistrarse.click();

            //Login
            WebElement campoUsuario = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("txtNombreUsuario")));
            WebElement campoContrasena = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("txtApellido")));
            WebElement btnLogin = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("IniciarSesionButton")));

            wait.until(ExpectedConditions.elementToBeClickable(campoUsuario));
            campoUsuario.sendKeys("jugador1");

            wait.until(ExpectedConditions.elementToBeClickable(campoContrasena));
            campoContrasena.sendKeys("123");

            wait.until(ExpectedConditions.elementToBeClickable(btnLogin));
            btnLogin.click();

            //Menu de jugador
            WebElement btnComercio = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("btnComercio")));
            wait.until(ExpectedConditions.elementToBeClickable(btnComercio));
            btnComercio.click();

            //Comerciar
            WebElement productoManzana = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"ProductosPlaneta\"]/div[2]")));
            wait.until(ExpectedConditions.elementToBeClickable(productoManzana));
            productoManzana.click();

            WebElement precioUnidadCompra = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("priceVenta")));
            String precioUnitariocompraManzana = precioUnidadCompra.getText();
            assertEquals(10,Float.parseFloat(precioUnitariocompraManzana.substring(1)));
            
            WebElement btnComprar = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("btnComprar")));
            wait.until(ExpectedConditions.elementToBeClickable(btnComprar));
            btnComprar.click();

            //InfoProducto
            WebElement addManzanas = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("plus")));
            wait.until(ExpectedConditions.elementToBeClickable(addManzanas));
            addManzanas.click();
            
            for (int i = 0; i < 9; i++) {
                addManzanas.click();
            }
            //Preguntarle al profe como puedo esperar hasta que se carguen los datos de la BDD para verificar precio unitario * 10 == total a pagar

        } catch (TimeoutException e) {
            fail("No se pudo encontrar el boton de registrarse");
        }
        assertEquals(true, true);
    }
    */
}
