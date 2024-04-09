import { Component } from '@angular/core';
import { AvatarService } from '../shared/avatar.service';
import { Avatar } from '../model/avatar';
import { NaveService } from '../shared/nave.service';
import { Nave } from '../model/nave';
import { ActivatedRoute, Router } from '@angular/router';
import { Planeta } from '../model/planeta';
import { PlanetaService } from '../shared/planeta.service';
import { Estrella } from '../model/estrella';
import { JugadorService } from '../shared/jugador.service';
import { EstrellaService } from '../shared/estrella.service';

@Component({
  selector: 'app-ver-info-estrella',
  templateUrl: './ver-info-estrella.component.html',
  styleUrl: './ver-info-estrella.component.css'
})
export class VerInfoEstrellaComponent {
  //Barra de arriba
  idJugador:number = 0
  estrellaJugador: Estrella = new Estrella(-1,"",-1,-1,-1);
  planetaNave: Planeta = new Planeta(-1,"Desconocido",false,"")
  jugadorNave:Nave = new Nave(-1,"",-1,1)
  
  EstrellaVista: Estrella = new Estrella(-1,"",-1,-1,-1);
  naves: Nave[] = [];
  planeta: Planeta[] = [];

  public planetas: Planeta[] = [new Planeta(-1,"",false,""),new Planeta(-1,"",false,""),new Planeta(-1,"",false,"")];

  constructor(
    private route: ActivatedRoute,
    private naveService: NaveService,
    private planetaService: PlanetaService,
    private jugadorService: JugadorService,
    private estrellaService: EstrellaService,
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

  cargarContenido():void {
    let idEstrella:number = 0
    this.route.params.subscribe(params => {
      idEstrella = Number(params['idEstrella']); 
    });
    this.estrellaService.obtenerEstrellaPorId(idEstrella).subscribe(estrella => {
      this.EstrellaVista = estrella;
    })
    this.naveService.listarNavesPorEstrella(idEstrella).subscribe(naves => {
      naves.forEach(element => {
        this.naveService.obtenerPlanetaPorNaveId(element.id).subscribe(planeta => {
          this.naves.push(element)
          if(planeta != null) {
            this.planeta.push(planeta);
          }
          else {
            this.planeta.push(new Planeta(-1,"Ciberespacio (Ningun planeta)",false,""))
            //alert("Poniendole un planeta de la estrella en h2 para la estrella donde esta, se mostrará el sistema solar")
          }
        })        
      });
    });

    this.planetaService.listarPlanetasPorId(idEstrella).subscribe(planetas => {
      for (let i = 0; i < planetas.length; i++) {
        this.planetas[i] = planetas[i]
      }
    });
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
