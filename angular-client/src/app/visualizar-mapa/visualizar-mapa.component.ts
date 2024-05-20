import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Nave } from '../model/nave';
import { JugadorService } from '../shared/jugador.service';
import { Estrella } from '../model/estrella';
import { NaveService } from '../shared/nave.service';
import { Planeta } from '../model/planeta';
import { CaminoService } from '../shared/camino.service';
import { Camino } from '../model/camino';
import { EstrellaDTO } from '../model/estrellaDTO';
import { PlanetaService } from '../shared/planeta.service';
import { Element } from '@angular/compiler';
import { UsuarioDTO } from '../model/usuario-dto';

@Component({
  selector: 'app-visualizar-mapa',
  templateUrl: './visualizar-mapa.component.html',
  styleUrl: './visualizar-mapa.component.css'
})
export class VisualizarMapaComponent {
  usuarioDTO: UsuarioDTO= new UsuarioDTO(0,"","","",0,"")
  
  //Barra de arriba
  idJugador:number = 0
  estrellaJugador: Estrella = new Estrella(-1,"",-1,-1,-1);
  planetaNave: Planeta = new Planeta(-1,"Desconocido",false,"")
  jugadorNave:Nave = new Nave(-1,"",-1,1)

  estrellasCamino: EstrellaDTO[] = [];

  //Para ver info o viajar
  filaSeleccionada: number | null = null;
  EstrellaSeleccionada:EstrellaDTO = new EstrellaDTO(-1,"",0,false);

  usuarioString: string|null= sessionStorage.getItem("infoJugador");

  constructor(
    private route: ActivatedRoute,
    private jugadorService: JugadorService,
    private naveService: NaveService,
    private caminoService: CaminoService,
    private planetaService: PlanetaService,
    private router: Router
  ) { }

  ngOnInit(): void {
    if(this.usuarioString){
      this.usuarioDTO= JSON.parse(this.usuarioString);
    }
    this.cargarBarra()
  }

  cargarBarra(): void {
    this.jugadorService.obtenerNaveJugador(this.usuarioDTO.id).subscribe(nave => {
      this.jugadorNave = nave
      this.naveService.obtenerEstrellaPorNaveId(this.jugadorNave.id).subscribe(estrella => {
        this.estrellaJugador = estrella
        this.cargarCaminos()
        
      })
      this.naveService.obtenerPlanetaPorNaveId(this.jugadorNave.id).subscribe(planeta => {
        if(planeta != null) {
          this.planetaNave = planeta
        }
      })
    })
  }

  cargarCaminos():void {
    this.caminoService.obtenerCaminosPorEstrellaId(this.estrellaJugador.id).subscribe(camino => {
      camino.forEach(element => {
        this.estrellasCamino.push(element)
      })
    })
    
  }

  infoEstrellaSeleccionada(id:number):void {
    this.router.navigate([`verInfoEstrella/${id}`]);
  }

  seleccionado(estrella:EstrellaDTO, index:number):void {
    this.EstrellaSeleccionada = estrella;
    this.filaSeleccionada = index;
  }

  cambiarPlanetaActual():void {
    this.router.navigate([`viajarPlaneta/${this.estrellaJugador.id}`]);
  }

  infoEstrellaActual():void {
    this.router.navigate([`verInfoEstrella/${this.estrellaJugador.id}`]);
  }

  viajarEstrellaSeleccionada(estrella:EstrellaDTO):void {
    this.planetaService.listarPlanetasPorId(estrella.id).subscribe(planetas => {
      if (planetas.length == 0) {
        this.router.navigate([`viajarEstrella/${estrella.id}`]);
      }
      else {
        this.router.navigate([`viajarPlaneta/${estrella.id}`]);
      }
    })
    
  }

  irMenu():void {
    this.router.navigate([`menu`]);
  }

  cerrarSesion() {
    this.router.navigate([``]);
  }

  
}
