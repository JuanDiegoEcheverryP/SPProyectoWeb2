package com.example.spaceinvaders.init;

import java.util.Random;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.spaceinvaders.model.Avatar;
import com.example.spaceinvaders.model.Camino;
import com.example.spaceinvaders.model.Estrella;
import com.example.spaceinvaders.model.Nave;
import com.example.spaceinvaders.model.Planeta;
import com.example.spaceinvaders.model.Producto;
import com.example.spaceinvaders.model.Producto_bodega;
import com.example.spaceinvaders.model.Stock_planeta;
import com.example.spaceinvaders.model.TipoNave;
import com.example.spaceinvaders.model.Jugador;
import com.example.spaceinvaders.repository.AvatarRepository;
import com.example.spaceinvaders.repository.CaminoRepository;
import com.example.spaceinvaders.repository.EstrellaRepository;
import com.example.spaceinvaders.repository.JugadorRepository;
import com.example.spaceinvaders.repository.NaveRepository;
import com.example.spaceinvaders.repository.PlanetaRepository;
import com.example.spaceinvaders.repository.ProductoBodegaRepository;
import com.example.spaceinvaders.repository.ProductoRepository;
import com.example.spaceinvaders.repository.StockPlanetaRepository;
import com.example.spaceinvaders.repository.TipoNaveRepository;


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

        //1200 planetas
        //15
        String[] planetanombre3={"Aquamarine","Puce","Blue","Mauv","Teal","Crimson","Violet","Yellow", "Khaki","Orange","Indigo","Maroon","Fuscia","Green","Red", "Turquoise"};
        //16
        String[] planetanombre2={"Lactulose","Naproxen","Lisinopril","Rifampin","Metoclopramide","Rifampin","Doxycycline","Spoonbill","Suricate","Elephant","dragon","Vulture","macaw","Lapwing","jackal"};
        //5
        String[] planetanombre1={"Planemo","Enana","Plutoide","Mesoplaneta","Protoplaneta"};

        
        
        Estrella estrella=new Estrella("enana roja",1,2,3);
        estrellaRepository.save(estrella);
        Planeta planeta=new Planeta("Saturno",true,"https://shorturl.at/kuFO8",estrella);
        planetaRepository.save(planeta);


        String[] tipoNaveNombre1={"nave", "nao"};
        String[] tipoNaveNombre2={"Violaceae", "Asteraceae","Orchidaceae", "Portulacaceae","Physciaceae","Aster","Sapotaceae","Hymenophyllaceae","Pinaceae","Astirrea"};
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

       

        String[] equipoNombre={"Mountain Marsh Larkspur","Riverswamp Nutrush","Ballhead Ragwort","Rough Goose Neck Moss","Cara De Caballo","Caracas Pepper","Prairie Pleuridium Moss","Fineflower Gilia","Eurasian Woodrush","Cartilage Lichen"};
        String nombreNave;
        Float credito, tiempo;
        int contador=0;
        List<Planeta> planetas = planetaRepository.findAll();
        List<TipoNave> tiposNave = tipoNaveRepository.findAll();

        for(String nombre : equipoNombre)
        {
            nombreNave=nombre;
            credito=rand.nextFloat() * 1000+1;
            tiempo=rand.nextFloat() * 10000+1;
            //Nave nave=new Nave(nombreNave,credito,tiempo,planetas.get(contador),tiposNave.get(contador));
            Nave nave=new Nave(nombreNave,credito,tiempo,planeta,tiposNave.get(contador));
            naveRepository.save(nave);
            contador++;
        }


        String[] avatarNombre={"Tails","Zero","Ness","Samus","Yoshi","Diddi","Geno"};
        String imagenAvatar="https://shorturl.at/gkJTU";

        for(String nombre : avatarNombre)
        {
            Avatar avatar=new Avatar(nombre,imagenAvatar);

            avatarRepository.save(avatar);
        }

 
        List<Nave> naves = naveRepository.findAll();
        List<Avatar> avatares = avatarRepository.findAll();

        String[] nombreJugadorPt1 = {"Cedric","Sorcha","Pacorro","Riobard","Cecilius","Cybil","Jasmina","Gizela","Nonie","Sileas"};
        String[] nombreJugadorPt2 = {"Turban","Float","Yateman","Tibols","Matthis","Elcy","Maidlow","Lamburne","Wadmore","Elcox"};

        String nombreJugador,contrasena;
        int contadorJugadores=0;

        for(String nombre1:nombreJugadorPt1 )
        {
            for(String nombre2:nombreJugadorPt2)
            {
                nombreJugador=nombre1+" "+nombre2;
                contrasena="12345";

                //poner capitanes
                if(contadorJugadores<10)
                {
                    jugadorRepository.save(new Jugador(nombreJugador,contrasena,CAPITAN,naves.get(contadorJugadores),avatares.get(rand.nextInt(7))));
                }
                //poner navegantes
                else if(contadorJugadores<20)
                {
                    jugadorRepository.save(new Jugador(nombreJugador,contrasena,PILOTO,naves.get(contadorJugadores-10),avatares.get(rand.nextInt(7))));
                }
                else
                {
                    jugadorRepository.save(new Jugador(nombreJugador,contrasena,COMERCIANTE,naves.get(rand.nextInt(9)),avatares.get(rand.nextInt(7))));
                }
                contadorJugadores++;
            }
        }

    
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
        
        //LLENAR LAS TABLAS INTERMEDIAS DE STOCK PLANETS Y BODEGA

        //AQUI PARA LLENAR EL STOCK
        List<Producto> productos = productoRepository.findAll();

        for (Producto producto : productos) {
            // Para cada planeta existente
            for (Planeta planetaa : planetas) {
                if(planeta.getHabitado())
                {
                    // Crear un nuevo stock para el producto en este planeta
                    Stock_planeta stockPlaneta = new Stock_planeta(rand.nextLong(1000000) + 1, rand.nextLong(1000000) + 1, rand.nextInt(200)); // Suponiendo que inicialmente hay 100 unidades
                    stockPlaneta.setProducto(producto);
                    stockPlaneta.setPlaneta(planetaa);
                    
                    // Guardar el stock del producto en el planeta
                    stockPlanetaRepository.save(stockPlaneta);
                    
                }
                
            }
        }

        //AQUI PARA LLENAR LA BODEGA
        int contadorNaves=0;

        for (int i=0; i< productos.size() && contadorNaves<10; contadorNaves++) {
             // Para cada nave existente
            for (int j=0; j<2; j++) {
                // Crear un nuevo stock para el producto en este planeta
                Producto_bodega productoBodega = new Producto_bodega(8, productos.get(i+j).getVolumen()*8); // Suponiendo que inicialmente hay 100 unidades
                productoBodega.setProducto(productos.get(i));
                productoBodega.setNave(naves.get(contadorNaves));
                productoBodegaRepository.save(productoBodega);
                i++;
            }
            // Guardar el producto después de asociar el stock
        }
        

    }
}