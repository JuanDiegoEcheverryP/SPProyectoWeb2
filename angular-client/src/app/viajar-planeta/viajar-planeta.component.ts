import { Component } from '@angular/core';
import { Planeta } from '../model/planeta';
import { PlanetaService } from '../shared/planeta.service';
import { ActivatedRoute, Router } from '@angular/router';
import { JugadorService } from '../shared/jugador.service';
import { NaveService } from '../shared/nave.service';
import { Estrella } from '../model/estrella';
import { Nave } from '../model/nave';
import { CaminoService } from '../shared/camino.service';

@Component({
  selector: 'app-viajar-planeta',
  templateUrl: './viajar-planeta.component.html',
  styleUrl: './viajar-planeta.component.css'
})
export class ViajarPlanetaComponent {
  //Barra de arriba
  idJugador:number = 0
  estrellaJugador: Estrella = new Estrella(-1,"",-1,-1,-1);
  planetaNave: Planeta = new Planeta(-1,"Desconocido",false,"")
  jugadorNave:Nave = new Nave(-1,"",-1,1)
  
  public planetas: Planeta[] = [new Planeta(-1,"",false,""),new Planeta(-1,"",false,""),new Planeta(-1,"",false,"")];
  planetaSeleccionado:number = -1

  costo:number = 0;

  constructor(
    private route: ActivatedRoute,
    private planetaService: PlanetaService,
    private jugadorService: JugadorService,
    private naveService: NaveService,
    private caminoService: CaminoService,
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
        }
        this.cargarContenido()
      })
    })
  }

  cargarContenido(): void {
    let idEstrella: number = -1;
    this.route.params.subscribe(params => {
      idEstrella = Number(params['idEstrella']); 
    });
    this.planetaService.listarPlanetasPorId(idEstrella).subscribe(planetas => {
      if(planetas.length != 0 && idEstrella != this.estrellaJugador.id) {
        this.caminoService.obtenerCaminoPorEstrellas(this.estrellaJugador.id,idEstrella).subscribe(camino => {
          if(camino.id == null) {
            this.router.navigate([`visualizarMapa/${this.idJugador}`]);
          }
          else {
            this.costo = camino.distancia;
          }
        })
      }
      else {
        
      }
      for (let i = 0; i < planetas.length; i++) {
        this.planetas[i] = planetas[i]
      }
    });
  }

  planetaSelected(id:number): void {
    this.planetaSeleccionado = id;
  }

  viajar():void {
    if(this.jugadorNave.tiempo < this.costo) {
      alert("La nave no tiene energia suficiente")
    }
    else {
      let idEstrella: number = -1;
      this.route.params.subscribe(params => {
        idEstrella = Number(params['idEstrella']); 
      });
      this.naveService.viajarConPlaneta(this.jugadorNave.id,idEstrella,this.planetas[this.planetaSeleccionado].id).subscribe(result =>{
        if(result) {
          this.router.navigate([`visualizarMapa/${this.idJugador}`]);
        }
        else {
          alert("Ha ocurrido un error")
        }
      })
      //Viajar, se le pasa idNave, id estrella, id planeta
    }
  }

  regresar() {
    this.route.params.subscribe(params => {
      this.idJugador = Number(params['idJugador']); 
    });
    this.router.navigate([`visualizarMapa/${this.idJugador}`]);
  }

  cerrarSesion() {
    this.router.navigate([``]);
  }
}
