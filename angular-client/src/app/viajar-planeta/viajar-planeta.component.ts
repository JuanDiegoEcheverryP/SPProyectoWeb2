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
    //Falta obtener el id de la ruta que no se como se hace en esta version de angular y debe reemplazarse en la siguiente linea
    this.planetaService.listarPlanetasPorId(3).subscribe(planetas => {
      for (let i = 0; i < planetas.length; i++) {
        this.planetas[i] = planetas[i]
      }
    });
  }
}
