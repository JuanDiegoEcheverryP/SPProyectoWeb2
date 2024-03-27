import { Component } from '@angular/core';
import { Planeta } from '../model/planeta';
import { PlanetaService } from '../shared/planeta.service';

@Component({
  selector: 'app-viajar-planeta',
  templateUrl: './viajar-planeta.component.html',
  styleUrl: './viajar-planeta.component.css'
})
export class ViajarPlanetaComponent {
  public planetas: Planeta[] = [new Planeta(-1,"",false,""),new Planeta(-1,"",false,""),new Planeta(-1,"",false,"")];

  constructor(
    private planetaService: PlanetaService,
  ) { }

  ngOnInit(): void {
    //Falta obtener estrella de origen
    //Falta obtener estrella final
    //Falta calcular mostrar el costo de viaje
    this.planetaService.listarPlanetasPorId().subscribe(planetas => this.planetas = planetas)
  }
}
