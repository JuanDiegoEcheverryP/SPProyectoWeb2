import { Component } from '@angular/core';
import { Estrella } from '../model/estrella';
import { EstrellaService } from '../shared/estrella.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Planeta } from '../model/planeta';
import { JugadorService } from '../shared/jugador.service';
import { NaveService } from '../shared/nave.service';
import { Nave } from '../model/nave';
import { CaminoService } from '../shared/camino.service';
import { UsuarioDTO } from '../model/usuario-dto';

@Component({
  selector: 'app-viajar-estrella',
  templateUrl: './viajar-estrella.component.html',
  styleUrl: './viajar-estrella.component.css'
})
export class ViajarEstrellaComponent {
  usuarioDTO: UsuarioDTO= new UsuarioDTO(0,"","","",0)
  
  //Barra de arriba
  idJugador:number = 0
  estrellaJugador: Estrella = new Estrella(-1,"",-1,-1,-1);
  planetaNave: Planeta = new Planeta(-1,"Desconocido",false,"")
  jugadorNave:Nave = new Nave(-1,"",-1,1)

  public estrella: Estrella = new Estrella(-1,"",-1,-1,-1);
  public planetas: Planeta[] = [new Planeta(-1,"",false,""),new Planeta(-1,"",false,""),new Planeta(-1,"",false,"")];

  costo:number = 0;

  usuarioString: string|null= sessionStorage.getItem("infoJugador");

  constructor(
    private route: ActivatedRoute,
    private estrellaService: EstrellaService,
    private jugadorService: JugadorService,
    private naveService: NaveService,
    private caminoService: CaminoService,
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
    this.estrellaService.obtenerEstrellaPorId(idEstrella).subscribe(estrella => {
      this.estrella = estrella;

      this.caminoService.obtenerCaminoPorEstrellas(this.estrellaJugador.id,idEstrella).subscribe(camino => {
        if(camino.id == null) {
          this.router.navigate([`visualizarMapa`]);
        }
        else {
          this.costo = camino.distancia;
        }
      })
    });
  }

  viajar(): void {
    if(this.jugadorNave.tiempo < this.costo) {
      alert("La nave no tiene energia suficiente")
    }
    else {
      let idEstrella: number = -1;
      this.route.params.subscribe(params => {
        idEstrella = Number(params['idEstrella']); 
      });
      this.naveService.viajarConPlaneta(this.jugadorNave.id,idEstrella,-1).subscribe(result =>{
        if(result) {
          this.router.navigate([`visualizarMapa`]);
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
    this.router.navigate([`visualizarMapa`]);
  }

  cerrarSesion() {
    this.router.navigate([``]);
  }
  
}
