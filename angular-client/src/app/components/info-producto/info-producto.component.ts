import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { InfoGeneralUsuarioService } from '../../shared/info-general-usuario.service';
import { ProductoBodegaService} from '../../shared/producto_bodega.service';
import { StockProductoService} from '../../shared/stockPlaneta.service';
import { NaveService} from '../../shared/nave.service';
import { TransaccionService} from '../../shared/transaccion.service';
import { CompraVentaDTO} from '../../model/compra-venta-dto';
import { ProductoDTO } from '../../model/productoDTO';
import { Planeta } from '../../model/planeta';
import { UsuarioDTO } from '../../model/usuario-dto';
import { RespuestaTransaccionDTO } from '../../model/respuesta-transaccion-dto';


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
  idJugador=0
  total:number=0
  volTotal:number=0
  producto:ProductoDTO= new ProductoDTO(0,"",0,"",0,0,0);
  nombreBoton:string=""
  compraVentaDTO:CompraVentaDTO= new CompraVentaDTO(0,0,0,0,0)
  @Output() eventChange = new EventEmitter<Event>();
  usuarioString: string|null= sessionStorage.getItem("infoJugador");
  usuarioDTO: UsuarioDTO= new UsuarioDTO(0,"","","",0,"")
  error: boolean = false;
  success: boolean = false;
  notificacionText:string=""

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

        console.error('Error compra', error);
        // Maneja cualquier error que pueda ocurrir durante la solicitud
      }
    );
  }

  obtenerInfoVenta()
  {
    console.log("infromacion info venta: nave",this.idNave," producto ",this.idProducto,"planeta: ",this.idPlaneta);
    
    this.bodega.recuperarProductosPorBodega(this.idNave,this.idProducto,this.idPlaneta).subscribe(
      (producto: ProductoDTO) => {
        console.log('Respuesta del backend:', producto);
        if(producto.nombre!=null)
        {
           this.producto=producto
        }
        else
        {
          this.producto.max=0
        }
        // Aquí puedes realizar cualquier acción con la respuesta del backend
      },
      (error) => {
        console.error('Error venta:', error);
        // Maneja cualquier error que pueda ocurrir durante la solicitud
      }
    );
  }

  obtenerPlaneta()
  {
    if(this.usuarioString)
    {
      this.usuarioDTO= JSON.parse(this.usuarioString);
      this.idNave=this.usuarioDTO.idNave
      this.idJugador=this.usuarioDTO.id
    }
    //this.idNave=this.shared.leerInformacion().idNave
    //this.idJugador=this.shared.leerInformacion().id
    //revisar porque el id esta mal
    console.log("id nave "+this.idNave, );

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

        console.log("producto",this.idProducto,"nave",this.idNave,"planeta",this.idPlaneta,"cantidad",this.cantidad,"total",this.total);
        
        if(this.cantidad>0)
        {
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
          this.error=true
          this.notificacionText="Cantidad debe ser mayor a cero"
        }
        
    }
    else
    {
      this.error=true
      this.notificacionText="cantidad invalida"
    }

  }

  comprar(event: Event){
    this.transaccionService.compra(this.compraVentaDTO).subscribe(
      (resp: RespuestaTransaccionDTO) => {
        console.log('Respuesta del backend:', resp);
        this.eventChange.emit(event); 
        this.success=true
        this.notificacionText=resp.mensaje
        this.obtenerInfoCompra()
        // Aquí puedes realizar cualquier acción con la respuesta del backend
      },
      (error) => {
        console.error('Error al hacer compra:', error);
        this.error=true
        this.notificacionText=error.error
        // Maneja cualquier error que pueda ocurrir durante la solicitud
      }
    );
  }

  vender(event: Event)
  {
    this.transaccionService.venta(this.compraVentaDTO).subscribe(
      (resp: RespuestaTransaccionDTO) => {
        console.log('Respuesta del backend:', resp);
        this.eventChange.emit(event);
        this.success=true
        this.notificacionText=resp.mensaje
        this.obtenerInfoVenta()
        // Aquí puedes realizar cualquier acción con la respuesta del backend
      },
      (error) => {
        console.error('Error al hacer venta:', error);
        this.error=true
        this.notificacionText=error.error
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
      if(this.informacionRecibir[0]==1)
      {
        //realizar la compra
        this.total = this.cantidad*this.producto.precioDemanda;
      }
      else if(this.informacionRecibir[0]==2)
      {
        //realizar la venta
        this.total = this.cantidad*this.producto.precioOferta;
      }
      console.log('Respuesta del backend:', this.total);
      this.volTotal = this.cantidad*this.producto.volumen;
    }
  }

  AceptarError()
  {
    this.error=false
  }

  AceptarSuccess()
  {
    this.success=false
  }

}


