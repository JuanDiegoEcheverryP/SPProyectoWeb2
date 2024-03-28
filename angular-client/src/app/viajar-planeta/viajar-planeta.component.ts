import { Component } from '@angular/core';
import { Planeta } from '../model/planeta';
import { PlanetaService } from '../shared/planeta.service';
import { ActivatedRoute } from '@angular/router';
import { JugadorService } from '../shared/jugador.service';
import { NaveService } from '../shared/nave.service';
import { Estrella } from '../model/estrella';
import { Nave } from '../model/nave';

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

  constructor(
    private route: ActivatedRoute,
    private planetaService: PlanetaService,
    private jugadorService: JugadorService,
    private naveService: NaveService,
  ) { }

  ngOnInit(): void {
    //Falta calcular mostrar el costo de viaje
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
      for (let i = 0; i < planetas.length; i++) {
        this.planetas[i] = planetas[i]
      }
    });
  }
}
