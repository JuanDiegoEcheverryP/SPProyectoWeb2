import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Nave } from '../model/nave';
import { JugadorService } from '../shared/jugador.service';
import { Estrella } from '../model/estrella';
import { NaveService } from '../shared/nave.service';
import { Planeta } from '../model/planeta';

@Component({
  selector: 'app-visualizar-mapa',
  templateUrl: './visualizar-mapa.component.html',
  styleUrl: './visualizar-mapa.component.css'
})
export class VisualizarMapaComponent {

  //Barra de arriba
  idJugador:number = 0
  estrellaJugador: Estrella = new Estrella(-1,"",-1,-1,-1);
  planetaNave: Planeta = new Planeta(-1,"Desconocido",false,"")
  jugadorNave:Nave = new Nave(-1,"",-1,1)

  constructor(
    private route: ActivatedRoute,
    private jugadorService: JugadorService,
    private naveService: NaveService,
  ) { }

  ngOnInit(): void {
    this.cargarBarra()
  }

  cargarBarra(): void {
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
      })
    })
  }
}
