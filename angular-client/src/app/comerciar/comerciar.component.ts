import { Component } from '@angular/core';
import { ProductoDTO } from '../model/productoDTO';
import { StockProductoService } from '../shared/stockPlaneta.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-comerciar',
  templateUrl: './comerciar.component.html',
  styleUrl: './comerciar.component.css'
})
export class ComerciarComponent {
  seleccionado: boolean = false;
  productoSeleccionado: ProductoDTO = new ProductoDTO(-1,"",-1,"",-1,-1,-1);

  productosPlaneta: ProductoDTO[] = [];

  constructor(
    private route: ActivatedRoute,
    private stockProductoService: StockProductoService,
  ) { }

  ngOnInit(): void {
    let a: number = -1;
    this.route.params.subscribe(params => {
      a = Number(params['idPlaneta']);
      console.log('Parámetros de la ruta:', params);
    });
    this.stockProductoService.listarProductosPlaneta(a).subscribe(productosPlaneta => {
      this.productosPlaneta = productosPlaneta;
      console.log(this.productosPlaneta);
      
    })
    
  }
  

  mostrarInformacion(Producto: ProductoDTO) {
    this.seleccionado = true;
    this.productoSeleccionado = Producto;
    console.log(this.productoSeleccionado);
    
  }
}
