import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-vender',
  templateUrl: './vender.component.html',
  styleUrl: './vender.component.css'
})
export class VenderComponent {
  id:number=0
  informacionEnviar:[number,number]=[2,0]
  event!: Event;

  constructor(private router: Router,  private route: ActivatedRoute) {} // Inyectar el servicio para iniciar sesión

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.id = Number(params['idProducto']); 
    });
    //envia la info al hijo para que haga su parte
    this.informacionEnviar[1]=this.id
  }

  onChange(event: Event) {
    this.event = event;
  }
}
