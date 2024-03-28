import { Component } from '@angular/core';
import { ProductoBodega } from '../model/producto_bodega';
import { ProductoBodegaService } from '../shared/producto_bodega.service';
import { Producto } from '../model/producto';
import { ProductoService } from '../shared/producto.service';
import { ProductoxproductoBodega } from '../model/productoxproductoBodega';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-bodega',
  templateUrl: './bodega.component.html',
  styleUrl: './bodega.component.css'
})
export class BodegaComponent {
  seleccionado: boolean = false;
  productoSeleccionado: ProductoxproductoBodega = new ProductoxproductoBodega(-1, "",-1,1,"");

  productosBodegaNave: ProductoxproductoBodega[] = [];
  productosBodega: ProductoBodega[] = [];
  productos: Producto[] = [];

  constructor(
    private route: ActivatedRoute,
    private productoBodega: ProductoBodegaService,
    private productoService: ProductoService,
  ) { }

  ngOnInit(): void {
    let idNave: number = -1;
    this.route.params.subscribe(params => {
      idNave = Number(params['idNave']); 
    });
    this.productoBodega.listarProductosPorNave(idNave).subscribe(productosBodega => {
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