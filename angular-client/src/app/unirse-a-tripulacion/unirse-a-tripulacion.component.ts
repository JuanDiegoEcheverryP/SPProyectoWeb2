import { Component } from '@angular/core';
import { TripulacionDTO } from '../model/tripulacion-dto';
import { NaveService } from '../shared/nave.service';
import { PatchRolNave } from '../model/patch-rol-nave';
import { JugadorService } from '../shared/jugador.service';
import { UsuarioDTO } from '../model/usuario-dto';
import { ActivatedRoute, Router } from '@angular/router';
import { InfoGeneralUsuarioService } from '../shared/info-general-usuario.service';


@Component({
  selector: 'app-unirse-a-tripulacion',
  templateUrl: './unirse-a-tripulacion.component.html',
  styleUrl: './unirse-a-tripulacion.component.css'
})
export class UnirseATripulacionComponent {

  tripulaciones: TripulacionDTO[] = [];
  patchRolNave: PatchRolNave = new PatchRolNave(0, "");
  id: number = 1

  constructor(private naveService: NaveService,  private route: ActivatedRoute, private jugadorSevice: JugadorService, private router: Router, private shared: InfoGeneralUsuarioService) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.id = Number(params['idJugador']); 
    });
    this.obtenerTripulaciones();
    this.patchRolNave.rol = 'comerciante';
  }

  obtenerTripulaciones() {
    this.naveService.listaTripulaciones().subscribe(
      (tripulaciones: TripulacionDTO[]) => {
        console.log('Respuesta del backend:', tripulaciones);
        this.tripulaciones = tripulaciones;
      },
      (error) => {
        console.error('Error al obtener tripulaciones:', error);
      }
    );
  }

  unirse() {
    //si escogio una nave 
    if (this.patchRolNave.naveId != 0) {
      this.jugadorSevice.modificarRolYNave(this.patchRolNave, this.id).subscribe(
        (usuario: UsuarioDTO) => {
          //this.shared.guardarInformacion(usuario)
          // Convertir el objeto usuario a una cadena JSON
          const usuarioString = JSON.stringify(usuario);
          
          // Guardar la cadena JSON en sessionStorage
          sessionStorage.setItem("infoJugador", usuarioString);
          this.router.navigate(['/menu']);
        },
        (error) => {
          console.error('Error al obtener usuario:', error);
        }
      );
    }
  }

  asignarTripulacion(naveId: number) {
    this.patchRolNave.naveId = naveId;
    console.log('naveId', naveId);
  }

  //poner notificacion ALERTA
  NotificarTripulacionLlena()
  {

  }

  onChangeRol(event: any) {
    // Esta función se llamará cada vez que cambie la selección del select
    // Aquí puedes acceder al valor seleccionado a través del evento
    // y asignarlo a la propiedad rolSeleccionado
    this.patchRolNave.rol = event.target.value;
    console.log('this.patchRolNave.rol', this.patchRolNave.rol);

  }

  selectedRowIndex: number = -1;

  seleccionarFila(index: number) {
    this.selectedRowIndex = index;
  }

}
