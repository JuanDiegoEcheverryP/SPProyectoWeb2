import { Component, ViewChild } from '@angular/core';
import { JugadorLogIn } from '../model/jugadorLogIn';
import {BarraMenuComponent } from '../components/barra-menu/barra-menu.component';
import { JugadorService } from '../shared/jugador.service';
import { UsuarioDTO } from '../model/usuario-dto';

@Component({
  selector: 'app-iniciarsesion',
  templateUrl: './iniciarsesion.component.html',
  styleUrl: './iniciarsesion.component.css'
})
export class IniciarsesionComponent {

  jugadorLogIn: JugadorLogIn = new JugadorLogIn("", ""); // Crear una instancia de JugadorLogIn

  constructor(private iniciarSesionService: JugadorService) {} // Inyectar el servicio para iniciar sesión

  iniciarSesion() {
    this.iniciarSesionService.iniciarSesion(this.jugadorLogIn).subscribe(
      (usuario: UsuarioDTO) => {
        console.log('Respuesta del backend:', usuario);
        // Aquí puedes realizar cualquier acción con la respuesta del backend
      },
      (error) => {
        console.error('Error al iniciar sesión:', error);
        // Maneja cualquier error que pueda ocurrir durante la solicitud
      }
    );
  }

}
