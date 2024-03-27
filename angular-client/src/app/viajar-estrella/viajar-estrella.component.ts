import { Component } from '@angular/core';
import { Estrella } from '../model/estrella';
import { EstrellaService } from '../shared/estrella.service';

@Component({
  selector: 'app-viajar-estrella',
  templateUrl: './viajar-estrella.component.html',
  styleUrl: './viajar-estrella.component.css'
})
export class ViajarEstrellaComponent {
  public estrella: Estrella = new Estrella(-1,"",-1,-1,-1);

  constructor(
    private estrellaService: EstrellaService,
  ) { }

  ngOnInit(): void {
    this.estrellaService.obtenerEstrellaPorId(2).subscribe(estrella => {
      this.estrella = estrella;
    });
  }
  
}
