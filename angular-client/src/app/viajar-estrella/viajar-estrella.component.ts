import { Component } from '@angular/core';
import { Estrella } from '../model/estrella';
import { EstrellaService } from '../shared/estrella.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-viajar-estrella',
  templateUrl: './viajar-estrella.component.html',
  styleUrl: './viajar-estrella.component.css'
})
export class ViajarEstrellaComponent {
  public estrella: Estrella = new Estrella(-1,"",-1,-1,-1);

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
