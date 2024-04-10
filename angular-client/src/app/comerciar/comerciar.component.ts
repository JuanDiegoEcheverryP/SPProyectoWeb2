import { Component } from '@angular/core';
import { ProductoDTO } from '../model/productoDTO';
import { StockProductoService } from '../shared/stockPlaneta.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Estrella } from '../model/estrella';
import { Planeta } from '../model/planeta';
import { Nave } from '../model/nave';
import { JugadorService } from '../shared/jugador.service';
import { NaveService } from '../shared/nave.service';
import { UsuarioDTO } from '../model/usuario-dto';

@Component({
  selector: 'app-comerciar',
  templateUrl: './comerciar.component.html',
  styleUrl: './comerciar.component.css'
})
export class ComerciarComponent {
  usuarioDTO: UsuarioDTO= new UsuarioDTO(0,"","","",0)
  
  //Barra de arriba
  idJugador:number = 0
  estrellaJugador: Estrella = new Estrella(-1,"",-1,-1,-1);
  planetaNave: Planeta = new Planeta(-1,"Desconocido",false,"")
  jugadorNave:Nave = new Nave(-1,"",-1,1)

  seleccionado: boolean = false;
  productoSeleccionado: ProductoDTO = new ProductoDTO(-1,"",-1,"",-1,-1,-1);

  productosPlaneta: ProductoDTO[] = [];

  usuarioString: string|null= sessionStorage.getItem("infoJugador");

  constructor(
    private route: ActivatedRoute,
    private stockProductoService: StockProductoService,
    private jugadorService: JugadorService,
    private naveService: NaveService,
    private router: Router
  ) { }

  ngOnInit(): void {
    if(this.usuarioString){
      this.usuarioDTO= JSON.parse(this.usuarioString);
    }
    this.cargar()
  }

  cargar(): void {
    this.jugadorService.obtenerNaveJugador(this.usuarioDTO.id).subscribe(nave => {
      this.jugadorNave = nave
      this.naveService.obtenerEstrellaPorNaveId(this.jugadorNave.id).subscribe(estrella => {
        this.estrellaJugador = estrella
      })
      this.naveService.obtenerPlanetaPorNaveId(this.jugadorNave.id).subscribe(planeta => {
        if(planeta != null) {
          this.planetaNave = planeta
          if(!planeta.habitado) {
            alert("El planeta no esta habitado")
          }
        }
        else {
          alert("No esta asignado un planeta donde esta la nave, esto tenemos que cambiarlo para que tambien asigne el planeta. Por lo tanto no se cargan los items")
        }
        
        this.cargarContenido()
      })
    })
  }

  cargarContenido(): void {
    this.stockProductoService.listarProductosPlaneta(this.planetaNave.id).subscribe(productosPlaneta => {
      this.productosPlaneta = productosPlaneta;
      
    })
  }
  

  mostrarInformacion(Producto: ProductoDTO) {
    this.seleccionado = true;
    this.productoSeleccionado = Producto;
  }

  irVender() {
    this.router.navigate([`vender/${this.productoSeleccionado.id}`]);
  }

  irComprar() {
    this.router.navigate([`comprar/${this.productoSeleccionado.id}`]);
  }

  regresar() {
    this.router.navigate([`menu`]);
  }

  cerrarSesion() {
    this.router.navigate([``]);
  }
}
