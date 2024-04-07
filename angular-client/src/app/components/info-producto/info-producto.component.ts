import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { InfoGeneralUsuarioService } from '../../shared/info-general-usuario.service';
import { ProductoBodegaService} from '../../shared/producto_bodega.service';
import { StockProductoService} from '../../shared/stockPlaneta.service';
import { NaveService} from '../../shared/nave.service';
import { TransaccionService} from '../../shared/transaccion.service';
import { CompraVentaDTO} from '../../model/compra-venta-dto';
import { ProductoDTO } from '../../model/productoDTO';
import { UsuarioDTO } from '../../model/usuario-dto';
import { Planeta } from '../../model/planeta';


@Component({
  selector: 'app-info-producto',
  templateUrl: './info-producto.component.html',
  styleUrl: './info-producto.component.css'
})
export class InfoProductoComponent {

  @Input() informacionRecibir: [number, number]=[-1,-1]
  cantidad:number=0
  idPlaneta:number=0
  idProducto:number=0
  idNave:number=0
  total:number=0
  volTotal:number=0
  producto:ProductoDTO= new ProductoDTO(0,"",0,"",0,0,0);
  nombreBoton:string=""
  compraVentaDTO:CompraVentaDTO= new CompraVentaDTO(0,0,0,0,0)
  @Output() eventChange = new EventEmitter<Event>();

  constructor(
    private nave: NaveService,
    private shared: InfoGeneralUsuarioService,
    private stock: StockProductoService, // Asegúrate de agregar el servicio aquí
    private bodega: ProductoBodegaService,
    private transaccionService: TransaccionService,
  ) {}

  ngOnInit(): void {
    console.log(this.informacionRecibir);
    this.obtenerPlaneta()
  }

  obtenerInfoCompra()
  {
    console.log(this.idProducto, this.idPlaneta);
    
    this.stock.recuperarProductosPorStock(this.idProducto,this.idPlaneta).subscribe(
      (producto: ProductoDTO) => {
        console.log('Respuesta del backend:', producto);
        this.producto=producto
        // Aquí puedes realizar cualquier acción con la respuesta del backend
      },
      (error) => {
        console.error('Error al iniciar sesión:', error);
        // Maneja cualquier error que pueda ocurrir durante la solicitud
      }
    );
  }

  obtenerInfoVenta()
  {
    this.bodega.recuperarProductosPorBodega(this.idNave,this.idProducto,this.idPlaneta).subscribe(
      (producto: ProductoDTO) => {
        console.log('Respuesta del backend:', producto);
        this.producto=producto
        // Aquí puedes realizar cualquier acción con la respuesta del backend
      },
      (error) => {
        console.error('Error al iniciar sesión:', error);
        // Maneja cualquier error que pueda ocurrir durante la solicitud
      }
    );
  }

  obtenerPlaneta()
  {
    this.idNave=this.shared.leerInformacion().idNave
    //revisar porque el id esta mal
    console.log("id nave "+this.idNave);

    this.nave.obtenerPlanetaPorNaveId(this.idNave).subscribe(
      (planeta: Planeta) => {
        console.log('Respuesta del backend:', planeta);
        //NO OLVIDAR
        this.idPlaneta=planeta.id

        this.idProducto=this.informacionRecibir[1]
        if(this.informacionRecibir[0]==1)
        {
          this.nombreBoton="comprar"
          //es una compra 
          this.obtenerInfoCompra()
        }
        else if(this.informacionRecibir[0]==2)
        {
          this.nombreBoton="vender"
          this.obtenerInfoVenta()
        }
      },
      (error) => {
        console.error('Error al obtener planeta:', error);
        // Maneja cualquier error que pueda ocurrir durante la solicitud
      }
    );
  }

  //manejar lo de ingresar el valor de comprar
  //realizar las validaciones necesarias
  //hacer peticion
  //obtener respuesta
  //si hay error imprimir en log por ahora
  transaccion(event: Event)
  {
    //si la cantidad es la correcta
    if(this.cantidad<=this.producto.max)
    {
        this.compraVentaDTO.idProducto=this.idProducto
        this.compraVentaDTO.idNave=this.idNave
        this.compraVentaDTO.idPlaneta=this.idPlaneta
        this.compraVentaDTO.cantidadProducto=this.cantidad
        this.compraVentaDTO.total=this.total

        console.log(this.idProducto,this.idNave,this.idPlaneta,this.cantidad,this.total);
        

        if(this.informacionRecibir[0]==1)
        {
          //realizar la compra
          this.comprar(event)
        }
        else if(this.informacionRecibir[0]==2)
        {
          //realizar la venta
          this.vender(event)
        }
    }
    else
    {
      console.log("cantidad invalida");
      
    }

  }

  comprar(event: Event){
    this.transaccionService.compra(this.compraVentaDTO).subscribe(
      (resp: string) => {
        console.log('Respuesta del backend:', resp);
        this.eventChange.emit(event); 
        // Aquí puedes realizar cualquier acción con la respuesta del backend
      },
      (error) => {
        console.error('Error al hacer compra:', error);
        this.eventChange.emit(event); 
        // Maneja cualquier error que pueda ocurrir durante la solicitud
      }
    );
  }

  vender(event: Event)
  {
    this.transaccionService.venta(this.compraVentaDTO).subscribe(
      (resp: string) => {
        console.log('Respuesta del backend:', resp);
        this.eventChange.emit(event);
        // Aquí puedes realizar cualquier acción con la respuesta del backend
      },
      (error) => {
        console.error('Error al hacer venta:', error);
        this.eventChange.emit(event); 
        // Maneja cualquier error que pueda ocurrir durante la solicitud
      }
    );
  }

  aumentar()
  {
    this.cantidad++
    this.actualizarTotales()
  }

  borrar()
  {
    this.cantidad=0
    this.actualizarTotales()
  }

  actualizarTotales() {
    if (!isNaN(this.cantidad)) {
      this.total = this.cantidad*this.producto.precioDemanda;
      console.log('Respuesta del backend:', this.total);
      this.volTotal = this.cantidad*this.producto.volumen;
    }
  }

}

//corregir consultas
  //corregir consulta inicio de sesion
  //corregir envio de id en usuario (no se envia)
  //poner en las consultas el codigo de error correspondiente
//conectar pantallas
//realizar manejo de errores desde el backend y mostrar notificaciones
//diseno total con responsiveness


