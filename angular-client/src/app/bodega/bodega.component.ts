import { Component } from '@angular/core';
import { ProductoBodega } from '../model/producto_bodega';
import { ProductoBodegaService } from '../shared/producto_bodega.service';
import { Producto } from '../model/producto';
import { ProductoService } from '../shared/producto.service';
import { ProductoxproductoBodega } from '../model/productoxproductoBodega';
import { ActivatedRoute, Router } from '@angular/router';
import { Estrella } from '../model/estrella';
import { Planeta } from '../model/planeta';
import { JugadorService } from '../shared/jugador.service';
import { Nave } from '../model/nave';
import { NaveService } from '../shared/nave.service';

@Component({
  selector: 'app-bodega',
  templateUrl: './bodega.component.html',
  styleUrl: './bodega.component.css'
})
export class BodegaComponent {
  //Barra de arriba
  idJugador:number = 0
  estrellaJugador: Estrella = new Estrella(-1,"",-1,-1,-1);
  planetaNave: Planeta = new Planeta(-1,"Desconocido",false,"")
  jugadorNave:Nave = new Nave(-1,"",-1,1)
  
  seleccionado: boolean = false;
  productoSeleccionado: ProductoxproductoBodega = new ProductoxproductoBodega(-1, "",-1,1,"");

  productosBodegaNave: ProductoxproductoBodega[] = [];
  productosBodega: ProductoBodega[] = [];
  productos: Producto[] = [];

  constructor(
    private route: ActivatedRoute,
    private productoBodega: ProductoBodegaService,
    private productoService: ProductoService,
    private jugadorService: JugadorService,
    private naveService: NaveService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.cargar()
  }

  cargar(): void {
    this.route.params.subscribe(params => {
      this.idJugador = Number(params['idJugador']); 
    });
    this.jugadorService.obtenerNaveJugador(this.idJugador).subscribe(nave => {
      this.jugadorNave = nave
      this.naveService.obtenerEstrellaPorNaveId(this.jugadorNave.id).subscribe(estrella => {
        this.estrellaJugador = estrella
      })
      this.naveService.obtenerPlanetaPorNaveId(this.jugadorNave.id).subscribe(planeta => {
        if(planeta != null) {
          this.planetaNave = planeta
        }
        this.cargarContenido()
      })
    })
  }

  cargarContenido() {
    let idNave: number = -1;
    this.route.params.subscribe(params => {
      idNave = Number(params['idNave']); 
    });
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

  cerrarSesion() {
    this.router.navigate([``]);
  }
}