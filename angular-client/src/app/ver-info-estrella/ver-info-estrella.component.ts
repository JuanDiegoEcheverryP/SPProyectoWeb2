import { Component } from '@angular/core';
import { AvatarService } from '../shared/avatar.service';
import { Avatar } from '../model/avatar';
import { NaveService } from '../shared/nave.service';
import { Nave } from '../model/nave';
import { ActivatedRoute } from '@angular/router';
import { Planeta } from '../model/planeta';
import { PlanetaService } from '../shared/planeta.service';

@Component({
  selector: 'app-ver-info-estrella',
  templateUrl: './ver-info-estrella.component.html',
  styleUrl: './ver-info-estrella.component.css'
})
export class VerInfoEstrellaComponent {
  naves: Nave[] = [];
  planeta: Planeta[] = [];

  public planetas: Planeta[] = [new Planeta(-1,"",false,""),new Planeta(-1,"",false,""),new Planeta(-1,"",false,"")];

  constructor(
    private route: ActivatedRoute,
    private naveService: NaveService,
    private planetaService: PlanetaService,
  ) { }

  ngOnInit(): void {
    let idEstrella: number = -1;
    this.route.params.subscribe(params => {
      idEstrella = Number(params['idEstrella']); 
    });
    this.naveService.listarNavesPorEstrella(idEstrella).subscribe(naves => {
      naves.forEach(element => {
        this.naveService.obtenerPlanetaPorNaveId(element.id).subscribe(planeta => {
          this.naves.push(element)
          this.planeta.push(planeta);
        })        
      });
    });

    this.planetaService.listarPlanetasPorId(idEstrella).subscribe(planetas => {
      for (let i = 0; i < planetas.length; i++) {
        this.planetas[i] = planetas[i]
      }
      console.log(this.planetas);
      
    });
  }

}
