import { Component } from '@angular/core';
import { NaveService } from '../shared/nave.service';
import { Nave } from '../model/nave';
import { TipoNave } from '../model/tipoNave';
import { tipoNaveService } from '../shared/tipoNave.service';

@Component({
  selector: 'app-registrar-nueva-tripulacion',
  templateUrl: './registrar-nueva-tripulacion.component.html',
  styleUrl: './registrar-nueva-tripulacion.component.css'
})
export class RegistrarNuevaTripulacionComponent {
  
  naves: TipoNave[] = [];

  constructor(
    private naveService: tipoNaveService,
  ) { }

  ngOnInit(): void {
    this.naveService.listarTipoNaves().subscribe(naves => this.naves = naves)
  }
}
