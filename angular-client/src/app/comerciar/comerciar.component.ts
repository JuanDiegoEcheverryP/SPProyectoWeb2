import { Component } from '@angular/core';
import { Nave } from '../model/nave';
import { NaveService } from '../shared/nave.service';

@Component({
  selector: 'app-comerciar',
  templateUrl: './comerciar.component.html',
  styleUrl: './comerciar.component.css'
})
export class ComerciarComponent {
  naves: Nave[] = [];

  constructor(
    private naveService: NaveService,
  ) { }

  ngOnInit(): void {
    this.naveService.listarNaves().subscribe(naves => this.naves = naves)
  }
}
