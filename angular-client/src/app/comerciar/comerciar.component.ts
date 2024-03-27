import { Component } from '@angular/core';
import { Nave } from '../model/nave';
import { NaveService } from '../shared/nave.service';
import { ProductoxproductoBodega } from '../model/productoxproductoBodega';
import { ProductoBodega } from '../model/producto_bodega';
import { Producto } from '../model/producto';
import { ProductoBodegaService } from '../shared/producto_bodega.service';
import { ProductoService } from '../shared/producto.service';

@Component({
  selector: 'app-comerciar',
  templateUrl: './comerciar.component.html',
  styleUrl: './comerciar.component.css'
})
export class ComerciarComponent {
  seleccionado: boolean = false;
  productoSeleccionado: ProductoxproductoBodega = new ProductoxproductoBodega(-1, "",-1,1,"");

  productosBodegaNave: ProductoxproductoBodega[] = [];
  productosBodega: ProductoBodega[] = [];
  productos: Producto[] = [];

  constructor(
    private productoBodega: ProductoBodegaService,
    private productoService: ProductoService,
  ) { }

  ngOnInit(): void {
    this.productoBodega.listarProductosPorNave(1).subscribe(productosBodega => {
      this.productosBodega = productosBodega;
      this.productoService.listarProductos().subscribe(productos => {
        this.productos = productos;
        productosBodega.forEach(element => {
          let a = new ProductoxproductoBodega(element.id, productos[element.id].nombre, element.cantidad, element.volTotal, productos[element.id].imagen)
          this.productosBodegaNave.push(a)
        });
        console.log(this.productosBodegaNave);
      })
    });
  }
  

  mostrarInformacion(producto: ProductoxproductoBodega) {
    this.seleccionado = true;
    this.productoSeleccionado = producto;
    console.log(this.productoSeleccionado);
    
  }
}
