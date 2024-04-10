import { Component, Input, SimpleChanges } from '@angular/core';
import { InfoGeneralUsuarioService } from '../../shared/info-general-usuario.service';
import { UsuarioDTO } from '../../model/usuario-dto';
import { NaveService } from '../../shared/nave.service';
import { NaveDTO } from '../../model/nave-dto';

@Component({
  selector: 'app-barra-estadisticas',
  templateUrl: './barra-estadisticas.component.html',
  styleUrl: './barra-estadisticas.component.css'
})
export class BarraEstadisticasComponent {
  usuarioDTO: UsuarioDTO= new UsuarioDTO(0,"","","",0)
  tiempoRestante: number=0
  creditos: number=0
  nave:NaveDTO= new NaveDTO("",0,"",0,"","",0,0,0,0,"",0)
  usuarioString: string|null= sessionStorage.getItem("infoJugador");

  constructor(private naveService: NaveService, private shared: InfoGeneralUsuarioService) { }

  ngOnInit() {
    if(this.usuarioString)
      {
        this.usuarioDTO= JSON.parse(this.usuarioString);
      }
    this.inicializarDatos()
  }

  @Input() informacionRecibir: any;


  @Input() 
  set eventChange(event: Event) {
    if (event) {
      this.inicializarDatos();
    }
  }

  inicializarDatos(){
    this.naveService.obtenerNave(this.usuarioDTO.idNave).subscribe(
      (nave: NaveDTO) => {
        console.log('Respuesta del backend:', nave);
        this.nave= nave;
      },
      (error) => {
        console.error('Error al obtener nave:', error);
      }
    );
  }


}
