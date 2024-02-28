package com.proyecto.spaceinvaders.NaveEspacial.init;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.proyecto.spaceinvaders.NaveEspacial.model.Camino;
import com.proyecto.spaceinvaders.NaveEspacial.model.Nave;
import com.proyecto.spaceinvaders.NaveEspacial.model.Planeta;
import com.proyecto.spaceinvaders.NaveEspacial.model.Producto;
import com.proyecto.spaceinvaders.NaveEspacial.model.Producto_bodega;
import com.proyecto.spaceinvaders.NaveEspacial.model.Stock_planeta;
import com.proyecto.spaceinvaders.NaveEspacial.model.TipoNave;
import com.proyecto.spaceinvaders.NaveEspacial.repository.AvatarRepository;
import com.proyecto.spaceinvaders.NaveEspacial.repository.CaminoRepository;
import com.proyecto.spaceinvaders.NaveEspacial.repository.EstrellaRepository;
import com.proyecto.spaceinvaders.NaveEspacial.repository.JugadorRepository;
import com.proyecto.spaceinvaders.NaveEspacial.repository.NaveRepository;
import com.proyecto.spaceinvaders.NaveEspacial.repository.PlanetaRepository;
import com.proyecto.spaceinvaders.NaveEspacial.repository.ProductoBodegaRepository;
import com.proyecto.spaceinvaders.NaveEspacial.repository.ProductoRepository;
import com.proyecto.spaceinvaders.NaveEspacial.repository.StockPlanetaRepository;
import com.proyecto.spaceinvaders.NaveEspacial.repository.TipoNaveRepository;


@Component
public class DBInitializer implements CommandLineRunner{
    @Autowired
    private AvatarRepository avatarRepository;

    @Autowired
    private CaminoRepository caminoRepository;

    @Autowired
    private EstrellaRepository estrellaRepository;

    @Autowired
    private JugadorRepository jugadorRepository;

    @Autowired
    private NaveRepository naveRepository;

    @Autowired
    private PlanetaRepository planetaRepository;

    @Autowired
    private ProductoBodegaRepository productoBodegaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private StockPlanetaRepository stockPlanetaRepository;

    @Autowired
    private TipoNaveRepository tipoNaveRepository;

    public static final String CAPITAN = "capitan";
    public static final String PILOTO = "piloto";
    public static final String COMERCIANTE = "comerciante";


    @Override
    public void run(String... args) throws Exception
    {

        Random rand = new Random();
        //40000 estrellas
        //400 estrellas tienen 1 a 3 estrellas
        //codas deben estar conectadas
        //100 jugadores
        //10 equipos
        //500 productos
        //20 naves

        //Nave base= new Nave();

        //PERSONAJES

        String[] tipoNaveNombre1={"nave", "nao"};
        String[] tipoNaveNombre2={"Violaceae", "Asteraceae","Orchidaceae", "Portulacaceae","Physciaceae","Asteraceae","Sapotaceae","Hymenophyllaceae","Pinaceae","Asteraceae"};
        String imagenNave="https://shorturl.at/tvwUW", nombreTipoNave;
        float volumenBodega, velocidad;

        for (String nombre1 : tipoNaveNombre1) {
            for (String nombre2 : tipoNaveNombre2) {

                nombreTipoNave=nombre1 + " " + nombre2;
                volumenBodega= rand.nextFloat() * 1000+1;
                velocidad=rand.nextFloat() * 100+1;
                TipoNave tipoNave=new TipoNave(nombreTipoNave,volumenBodega,velocidad,imagenNave);
                tipoNaveRepository.save(tipoNave);
            }
        }


        String[] nombreJugadorPt1 = {"Cedric","Sorcha","Pacorro","Riobard","Cecilius","Cybil","Jasmina","Gizela","Nonie","Sileas"};
        String[] nombreJugadorPt2 = {"Turban","Float","Yateman","Tibols","Matthis","Elcy","Maidlow","Lamburne","Wadmore","Elcox"};

        String nombreJugador,contrasena;



        String[] equipoNombre={"Mountain Marsh Larkspur","Riverswamp Nutrush","Ballhead Ragwort","Rough Goose Neck Moss","Cara De Caballo","Caracas Pepper","Prairie Pleuridium Moss","Fineflower Gilia","Eurasian Woodrush","Cartilage Lichen"};
        String nombreNave;
        Float credito, tiempo;


    
        String[] nombreProducto1={"Goldenrod","Mauv","Turquoise","Puce","Khaki","Orange","Pink","Red","Indigo","Violet"};
        String[] nombreProducto2={"Cheese","Beef","Cumin","Pepper","Curry Powder","Bread","Eggs","Napkin","Pumpkin","Pasta"};
        String[] nombreProducto3={"Magpie","Pygmy","Turtle","barbet","Goose"};

        String nombreProducto;
        String imagenProducto="https://shorturl.at/afzIM";
        float volumen;

        //CREACION DE PRODUCTOS
        for (String nombre1 : nombreProducto1) {
            for (String nombre2 : nombreProducto2) {
                for (String nombre3 : nombreProducto3) {
                    volumen= rand.nextFloat() * 100+1;
                    nombreProducto=nombre1 + " " + nombre2 + " " + nombre3;
                    Producto producto=new Producto(nombreProducto,imagenProducto,volumen);
                    productoRepository.save(producto);
                }
            }
        }
        
        
/* 
        //1200 planetas
        //15
        String[] planetanombre3={"Aquamarine","Puce","Blue","Mauv","Teal","Crimson","Violet","Yellow", "Khaki","Orange","Indigo","Maroon","Fuscia","Green","Red", "Turquoise"};
        //16
        String[] planetanombre2={"Lactulose","Naproxen","Lisinopril","Rifampin","Metoclopramide","Rifampin","Doxycycline","Spoonbill","Suricate","Elephant","dragon","Vulture","macaw","Lapwing","jackal"};
        //5
        String[] planetanombre1={"Planemo","Enana","Plutoide","Mesoplaneta","Protoplaneta"};

        /*
        //LLENAR LAS TABLAS INTERMEDIAS DE STOCK PLANETS Y BODEGA

        //AQUI PARA LLENAR EL STOCK
        List<Planeta> planetas = planetaRepository.findAll();
        List<Producto> productos = productoRepository.findAll();

        for (Producto producto : productos) {
            // Para cada planeta existente
            for (Planeta planeta : planetas) {
                // Crear un nuevo stock para el producto en este planeta
                Stock_planeta stockPlaneta = new Stock_planeta(rand.nextLong() * 1000001, rand.nextLong() * 1000001, rand.nextInt(200)); // Suponiendo que inicialmente hay 100 unidades
                stockPlaneta.setProducto(producto);
                stockPlaneta.setPlaneta(planeta);
                
                // Guardar el stock del producto en el planeta
                stockPlanetaRepository.save(stockPlaneta);
                
                // Asociar el stock al producto
                producto.anadirStockPlaneta(stockPlaneta);
                
                // Asociar el stock al planeta
                planeta.anadirStockPlaneta(stockPlaneta);

                planetaRepository.save(planeta);
            }
            // Guardar el producto después de asociar el stock
            productoRepository.save(producto);
        }
       

        //AQUI PARA LLENAR LA BODEGA

        List<Nave> naves = naveRepository.findAll();
        int contador=0;

        for (Producto producto : productos) {
             // Para cada nave existente
            for (int i=0; i<2; i++) {
                // Crear un nuevo stock para el producto en este planeta
                Producto_bodega productoBodega = new Producto_bodega(8, producto.getVolumen()*8); // Suponiendo que inicialmente hay 100 unidades
                productoBodega.setProducto(producto);
                productoBodega.setNave(naves.get(contador));
                
                // Guardar el stock del producto en el planeta
                productoBodegaRepository.save(productoBodega);
                
                // Asociar el stock al producto
                producto.anadirProductoBodega(productoBodega);
                
                // Asociar el stock al planeta
                naves.get(contador).anadirProductoBodega(productoBodega);

                naveRepository.save(naves.get(contador));

                contador++;
            }
            // Guardar el producto después de asociar el stock
            productoRepository.save(producto);
        }
        
        */
    }
}