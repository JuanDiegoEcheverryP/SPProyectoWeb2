import { Component } from '@angular/core';
import { Estrella } from '../model/estrella';
import { EstrellaService } from '../shared/estrella.service';
import { ActivatedRoute } from '@angular/router';
import { Planeta } from '../model/planeta';

@Component({
  selector: 'app-viajar-estrella',
  templateUrl: './viajar-estrella.component.html',
  styleUrl: './viajar-estrella.component.css'
})
export class ViajarEstrellaComponent {
  public estrella: Estrella = new Estrella(-1,"",-1,-1,-1);
  public planetas: Planeta[] = [new Planeta(-1,"",false,""),new Planeta(-1,"",false,""),new Planeta(-1,"",false,"")];

  constructor(
    private route: ActivatedRoute,
    private estrellaService: EstrellaService,
  ) { }

  ngOnInit(): void {
    let idEstrella: number = -1;
    this.route.params.subscribe(params => {
      idEstrella = Number(params['idEstrella']); 
    });
    this.estrellaService.obtenerEstrellaPorId(idEstrella).subscribe(estrella => {
      this.estrella = estrella;
    });
  }
  
}
