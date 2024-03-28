import { Component } from '@angular/core';
import { Planeta } from '../model/planeta';
import { PlanetaService } from '../shared/planeta.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-viajar-planeta',
  templateUrl: './viajar-planeta.component.html',
  styleUrl: './viajar-planeta.component.css'
})
export class ViajarPlanetaComponent {
  
  public planetas: Planeta[] = [new Planeta(-1,"",false,""),new Planeta(-1,"",false,""),new Planeta(-1,"",false,"")];

  constructor(
    private route: ActivatedRoute,
    private planetaService: PlanetaService,
  ) { }

  ngOnInit(): void {
    //Falta obtener estrella de origen
    //Falta obtener estrella final
    //Falta calcular mostrar el costo de viaje
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
