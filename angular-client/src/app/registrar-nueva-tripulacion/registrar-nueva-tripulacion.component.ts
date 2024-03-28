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
  actual: TipoNave = new TipoNave(1,"ss",1,1,"dd");
  indice:number = -1;

  constructor(
    private naveService: tipoNaveService,
  ) { }

  ngOnInit(): void {
    //this.naveService.listarTipoNaves().subscribe(naves => this.naves = naves)
    this.naveService.listarTipoNaves().subscribe(naves => {
      this.naves = naves
      this.actual = this.naves[0];
      this.indice = 0;
      console.log(this.actual);
      
    });
  }

  siguienteNave() {
    console.log(this.indice);
    
    if(this.indice == this.naves.length-1) {
      this.actual = this.naves[0];
      this.indice = 0;
    }
    else {
      this.actual = this.naves[this.indice+1];
      this.indice += 1;
    }
  }

  anteriorNave() {
    console.log(this.indice);
    
    if(this.indice == 0) {
      this.actual = this.naves[this.naves.length-1];
      this.indice = this.naves.length-1;
    }
    else {
      this.actual = this.naves[this.indice-1];
      this.indice -= 1;
    }
  }
}
