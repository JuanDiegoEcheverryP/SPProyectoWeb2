import { Component } from '@angular/core';
import { AvatarService } from '../shared/avatar.service';
import { Avatar } from '../model/avatar';
import { ProductoBodega } from '../model/producto_bodega';
import { ProductoBodegaService } from '../shared/producto_bodega.service';

@Component({
  selector: 'app-bodega',
  templateUrl: './bodega.component.html',
  styleUrl: './bodega.component.css'
})
export class BodegaComponent {
  productosBodega: ProductoBodega[] = [];

  constructor(
    private productoBodega: ProductoBodegaService,
  ) { }

  ngOnInit(): void {
    this.productoBodega.listarProductosPorNave(1).subscribe(productos => {
      this.productosBodega = productos;
      console.log(this.productosBodega);
    });
  }
}
