package com.example.spaceinvaders.init;

import java.util.Random;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Optional;

import com.example.spaceinvaders.model.Avatar;
import com.example.spaceinvaders.model.Camino;
import com.example.spaceinvaders.model.Estrella;
import com.example.spaceinvaders.model.Nave;
import com.example.spaceinvaders.model.Planeta;
import com.example.spaceinvaders.model.Producto;
import com.example.spaceinvaders.model.ProductoBodega;
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
    private  Random rand = new Random();
    
    @Override
    public void run(String... args) throws Exception
    {

        //Estrella estrella=new Estrella("enana roja",1,2,3);
        //estrellaRepository.delete(estrella);

        //40000 estrellas
        //400 estrellas tienen 1 a 3 estrellas
        //codas deben estar conectadas
        //100 jugadores
        //10 equipos
        //500 productos
        //20 naves

        System.out.println("Cargando estrellas");
        insertarEstrellas();

        System.out.println("Cargando caminos");
        //insetarCaminos();

        System.out.println("Cargando planetas");
        insetarPlanetas();

        System.out.println("Cargando tiposNave");
        insetarTiposNave();

        System.out.println("Cargando naves");
        insetarNaves();

        System.out.println("Cargando Avatares");
        insetarAvatares();

        System.out.println("Cargando Jugadores");
        insetarJuagadores();

        System.out.println("Cargando Productos");
        insetarProductos();

        //AQUI PARA LLENAR EL STOCK
        System.out.println("Cargando Planetas");
       insetarStockPlaneta();
 
        //AQUI PARA LLENAR LA BODEGA
        System.out.println("Cargando ProductosBodega");
        insetarProductoBodega();

        System.out.println("Se han cargado todos los datos");
    }

    private float calcularDistancia(float x,float x2,float y,float y2,float z,float z2)
    {
        float distancia=0f;
       
        if(x==x2 && y==y2 && z==z2)
        {
            return -1f;
        }

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

    private void insertarEstrellas()
    {
        String[] estrellaNombre1={"Alfa", "Bravo", "Charlie", "Delta", "Echo", "Foxtrot", "Golf", "Hotel", "India", "Juliett", "Kilo", "Lima", "Mike", "November", "Oscar", "Papa", "Quebec", "Romeo", "Sierra", "Tango", "Uniform", "Victor", "Whiskey", "Xray", "Yankee", "Zulu",  "Gamma",  "Deltaris", "Epsilon", "Zeta", "Theta", "Kappa", "Lambda", "Omicron", "Omega"};
        String[] estrellaNombre2= {"Canis", "Polar", "Borealis", "Betelgeuse", "Canopo", "Scuti", "Rigel", "Sirio", "Canopus", "Albirero", "Lusitania", "Libertas", "Mimosa", "Nekkar", "Polaris", "Rigelia", "Pegasus", "Orion", "Azrael", "Atlas", "Belenos", "Celaneo", "Helvetios", "Intercrus", "Australis", "Borealisis", "Macondo", "Columbae", "Capricorni", "Coronae", "Vega", "Centauri", "Carina", "Alnilam", "Hadar"} ;
        String[] estrellaNombre3= {"azul","blanco azulada","blanco amarillento","amarillo","anaranjado amarillento","anaranjado","anaranjado rojizo","rojo naranja","rojo","carmesi","cafe","marron","enana","subenana","sub gigante","gigante","supergigante","colosa","super colosa","masiva","super masiva","hipergigante","brillante","super brillante","opaca","errante","binaria","ternaria","cambiante","duende","neutra","extrana","anormal","pulsar","albireo"};

        int contadorEstrellas = 0;
        String nombreEstrella;

        for (int i=0; i<estrellaNombre1.length && contadorEstrellas<40000; i++) {
            for (int j=0; j<estrellaNombre2.length && contadorEstrellas<40000; j++) {
                for (int k=0; k<estrellaNombre3.length && contadorEstrellas<40000; k++) {

                    nombreEstrella=estrellaNombre1[i]+" "+estrellaNombre2[j]+" "+estrellaNombre3[k];
                    Estrella estrella= new Estrella(nombreEstrella,i*(300/40)+rand.nextFloat(1),j*(300/40)+rand.nextFloat(1),k*(300/40)+rand.nextFloat(1));
                    estrellaRepository.save(estrella);
                    contadorEstrellas++;
                }
            }
        }
    }

    private void insetarCaminos()
    {
        List<Estrella> estrellas = estrellaRepository.findAll();
        int inicio;
        Estrella ref,destino;
        Float distancia;
        //INGRESAR ESTRELLAS 
        //PRIMERO QUE CADA ESTRELLA NUMERO 5000 SE CONECTE CON LAS 5000 DE ABAJO

        /*
        //Esto es un codigo de prueba de los controladores
        Avatar a = new Avatar("uno","hola");
        Avatar b = new Avatar("dos","hola");
        Avatar c = new Avatar("tres","hola");
        
        avatarRepository.save(a);
        avatarRepository.save(b);
        avatarRepository.save(c);

        TipoNave f = new TipoNave("test", (float)5.5, (float) 6.6, "Hola");
        tipoNaveRepository.save(f);
         */
        for(int i=0; i<7;i++)
        {
            ref=estrellas.get((i+1)*5000-1);
            if(i==0)
            {
                inicio=5000*i;
            }
            else
            {
                inicio=5000*i-1;
            }
                
            for(;inicio<5000+5000*i-1;inicio++)
            {
                destino=estrellas.get(inicio);

                distancia=calcularDistancia(ref.getCoord_x(),destino.getCoord_x(),ref.getCoord_y(),destino.getCoord_y(),ref.getCoord_z(),destino.getCoord_z());

                Camino caminoIda= new Camino(ref, destino,"caminoCincomiles",distancia);
                caminoRepository.save(caminoIda);
                Camino caminoVuelta= new Camino(destino,ref,"caminoCincomiles",distancia);
                caminoRepository.save(caminoVuelta);
            }
        }
        
        //DESPUES CONEXIONES ALEATORIAS
        for(Estrella estrella: estrellas)
        {
            for(int i=0; i<3; i++)
            {
                destino=estrellas.get(rand.nextInt(40000));
                distancia=calcularDistancia(estrella.getCoord_x(),destino.getCoord_x(),estrella.getCoord_y(),destino.getCoord_y(),estrella.getCoord_z(),destino.getCoord_z());

                Optional<Camino> caminoExistente = caminoRepository.findByEstrellaInicioAndEstrellaFinal(estrella, destino);
                
                if (!caminoExistente.isPresent() && distancia!=-1) {
                    Camino caminoIda= new Camino(estrella, destino,"camino",distancia);
                    caminoRepository.save(caminoIda);
                    Camino caminoVuelta= new Camino(destino,estrella,"camino",distancia);
                    caminoRepository.save(caminoVuelta);
                }   
            }
        }
    }

    private void insetarAvatares()
    {
        String[] avatarNombre={"Tails","Zero","Ness","Samus","Yoshi","Diddi","Geno"};
        String imagenAvatar="https://shorturl.at/gkJTU";

        for(String nombre : avatarNombre)
        {
            Avatar avatar=new Avatar(nombre,imagenAvatar);

            avatarRepository.save(avatar);
        }
    }

    private void insetarJuagadores()
    {
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
    }

    private void insetarNaves()
    {
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
            Nave nave=new Nave(nombreNave,credito,tiempo,planetas.get(rand.nextInt(1200)),tiposNave.get(contador));
            naveRepository.save(nave);
            contador++;
        }

    }

    private void insetarPlanetas()
    {
        List<Estrella> estrellas = estrellaRepository.findAll();

        //1200 planetas
        //15
        String[] planetanombre3={"Aquamarine","Puce","Blue","Mauv","Teal","Crimson","Violet","Yellow", "Khaki","Orange","Indigo","Maroon","Fuscia","Green","Red", "Turquoise"};
        //16
        String[] planetanombre2={"Lactulose","Naproxen","Lisinopril","Rifampin","Metoclopramide","Rifampinmin","Doxycycline","Spoonbill","Suricate","Elephant","dragon","Vulture","macaw","Lapwing","jackal"};
        //5
        String[] planetanombre1={"Planemo","Enana","Plutoide","Mesoplaneta","Protoplaneta"};

        String planetaNombre;
        Boolean habitado;
        String imagen="https://shorturl.at/kuFO8";
        int planetaXestrella=0,contEstrella=0;

        for (String nombre1 : planetanombre1) {
            for (String nombre2 : planetanombre2) {
                for (String nombre3 : planetanombre3) {

                    planetaNombre=nombre1+" "+nombre2+" "+nombre3;

                    if(contEstrella<400)
                    {
                        habitado=true;
                    }
                    else
                    {
                        habitado=false;
                    }

                    if(planetaXestrella==0)
                    {
                        planetaXestrella=rand.nextInt(3) + 1;
                        Planeta planeta=new Planeta(planetaNombre,habitado,imagen,estrellas.get(contEstrella));
                        planetaRepository.save(planeta);
                        planetaXestrella--;
                        contEstrella++;
                    }
                    else
                    {
                        Planeta planeta=new Planeta(planetaNombre,habitado,imagen,estrellas.get(contEstrella));
                        planetaRepository.save(planeta);
                        planetaXestrella--;
                    }

                }
            }
        }
  
    }

    private void insetarTiposNave()
    {
        String[] tipoNaveNombre1={"nave", "nao"};
        String[] tipoNaveNombre2={"Violaceae", "Asteraceae","Orchidaceae", "Portulacaceae","Physciaceae","Aster","Sapotaceae","Hymenophyllaceae","Pinaceae","Astirrea"};
        String imagenNave="https://shorturl.at/tvwUW", nombreTipoNave;
        float volumenBodega, velocidad;

        for (String nombre1 : tipoNaveNombre1) {
            for (String nombre2 : tipoNaveNombre2) {

                nombreTipoNave=nombre1 + " " + nombre2;
                volumenBodega= rand.nextFloat(1000) +1;
                velocidad=rand.nextFloat(100) +1;
                TipoNave tipoNave=new TipoNave(nombreTipoNave,volumenBodega,velocidad,imagenNave);
                tipoNaveRepository.save(tipoNave);
            }
        }
    }

    private void insetarProductos()
    {
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
    }


    private void insetarStockPlaneta()
    {
        List<Producto> productos = productoRepository.findAll();
        List<Planeta> planetas = planetaRepository.findAll();

        for (Producto producto : productos) {
            // Para cada planeta existente
            for (Planeta planeta : planetas) {
                if(planeta.getHabitado())
                {
                    // Crear un nuevo stock para el producto en este planeta
                    Stock_planeta stockPlaneta = new Stock_planeta(rand.nextLong(1000000) + 1, rand.nextLong(1000000) + 1, rand.nextInt(200)); // Suponiendo que inicialmente hay 100 unidades
                    stockPlaneta.setProducto(producto);
                    stockPlaneta.setPlaneta(planeta);
                    
                    // Guardar el stock del producto en el planeta
                    stockPlanetaRepository.save(stockPlaneta);
                    
                }
                
            }
        }

    }

    private void insetarProductoBodega()
    {
        List<Producto> productos = productoRepository.findAll();
        List<Nave> naves = naveRepository.findAll();
        int contadorNaves=0;

        for (int i=0; i< productos.size() && contadorNaves<10; contadorNaves++) {
             // Para cada nave existente
            for (int j=0; j<2; j++) {
                // Crear un nuevo stock para el producto en este planeta
                ProductoBodega productoBodega = new ProductoBodega(8, productos.get(i+j).getVolumen()*8); // Suponiendo que inicialmente hay 100 unidades
                productoBodega.setProducto(productos.get(i));
                productoBodega.setNave(naves.get(contadorNaves));
                productoBodegaRepository.save(productoBodega);
                i++;
            }
            // Guardar el producto después de asociar el stock
        }
    }
}