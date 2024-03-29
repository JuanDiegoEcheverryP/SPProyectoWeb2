import { Component } from '@angular/core';
import { ProductoDTO } from '../model/productoDTO';
import { StockProductoService } from '../shared/stockPlaneta.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Estrella } from '../model/estrella';
import { Planeta } from '../model/planeta';
import { Nave } from '../model/nave';
import { JugadorService } from '../shared/jugador.service';
import { NaveService } from '../shared/nave.service';

@Component({
  selector: 'app-comerciar',
  templateUrl: './comerciar.component.html',
  styleUrl: './comerciar.component.css'
})
export class ComerciarComponent {
  //Barra de arriba
  idJugador:number = 0
  estrellaJugador: Estrella = new Estrella(-1,"",-1,-1,-1);
  planetaNave: Planeta = new Planeta(-1,"Desconocido",false,"")
  jugadorNave:Nave = new Nave(-1,"",-1,1)

  seleccionado: boolean = false;
  productoSeleccionado: ProductoDTO = new ProductoDTO(-1,"",-1,"",-1,-1,-1);

  productosPlaneta: ProductoDTO[] = [];

  constructor(
    private route: ActivatedRoute,
    private stockProductoService: StockProductoService,
    private jugadorService: JugadorService,
    private naveService: NaveService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.cargar()
  }

  cargar(): void {
    this.route.params.subscribe(params => {
      this.idJugador = Number(params['idJugador']); 
    });
    this.jugadorService.obtenerNaveJugador(this.idJugador).subscribe(nave => {
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
        console.log(planeta);
        
        this.cargarContenido()
      })
    })
  }

  cargarContenido(): void {
    this.stockProductoService.listarProductosPlaneta(this.planetaNave.id).subscribe(productosPlaneta => {
      this.productosPlaneta = productosPlaneta;
      console.log(this.productosPlaneta);
      
    })
  }
  

  mostrarInformacion(Producto: ProductoDTO) {
    this.seleccionado = true;
    this.productoSeleccionado = Producto;
    console.log(this.productoSeleccionado);
    
  }

  cerrarSesion() {
    this.router.navigate([``]);
  }
}
