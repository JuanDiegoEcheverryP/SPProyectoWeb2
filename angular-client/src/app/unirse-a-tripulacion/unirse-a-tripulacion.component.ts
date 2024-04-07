import { Component } from '@angular/core';
import { TripulacionDTO } from '../model/tripulacion-dto';
import { NaveService } from '../shared/nave.service';
import { PatchRolNave } from '../model/patch-rol-nave';
import { JugadorService } from '../shared/jugador.service';
import { UsuarioDTO } from '../model/usuario-dto';
import { Router } from '@angular/router';
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

  constructor(private naveService: NaveService, private jugadorSevice: JugadorService, private router: Router, private shared: InfoGeneralUsuarioService) { }

  ngOnInit() {
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
          console.log('Respuesta del backend:', usuario);
          this.shared.guardarInformacion(usuario)
          console.log('Respuesta del backend:', this.shared.leerInformacion().idNave);
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

  onChangeRol(event: any) {
    // Esta función se llamará cada vez que cambie la selección del select
    // Aquí puedes acceder al valor seleccionado a través del evento
    // y asignarlo a la propiedad rolSeleccionado
    this.patchRolNave.rol = event.target.value;
    console.log('this.patchRolNave.rol', this.patchRolNave.rol);

  }

}
