import { Component } from '@angular/core';
import { Router,ActivatedRoute} from '@angular/router';


@Component({
  selector: 'app-comprar',
  templateUrl: './comprar.component.html',
  styleUrl: './comprar.component.css'
})
export class ComprarComponent {

  id:number=0
  informacionEnviar:[number,number]=[1,0]
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
