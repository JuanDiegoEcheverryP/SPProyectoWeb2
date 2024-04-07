import { Component, ViewChild } from '@angular/core';
import { JugadorLogIn } from '../model/jugadorLogIn';
import {BarraMenuComponent } from '../components/barra-menu/barra-menu.component';
import { JugadorService } from '../shared/jugador.service';
import { UsuarioDTO } from '../model/usuario-dto';
import { Router } from '@angular/router';
import { InfoGeneralUsuarioService } from '../shared/info-general-usuario.service';

@Component({
  selector: 'app-iniciarsesion',
  templateUrl: './iniciarsesion.component.html',
  styleUrl: './iniciarsesion.component.css'
})
export class IniciarsesionComponent {

  jugadorLogIn: JugadorLogIn = new JugadorLogIn("", ""); // Crear una instancia de JugadorLogIn

  constructor(private iniciarSesionService: JugadorService,private router: Router, private shared:InfoGeneralUsuarioService) {} // Inyectar el servicio para iniciar sesión

  iniciarSesion() {
    this.iniciarSesionService.iniciarSesion(this.jugadorLogIn).subscribe(
      (usuario: UsuarioDTO) => {
        console.log('Respuesta del backend:', usuario);
        this.shared.guardarInformacion(usuario)
        this.router.navigate(['/menu']);
        // Aquí puedes realizar cualquier acción con la respuesta del backend
      },
      (error) => {
        console.error('Error al iniciar sesión:', error);
        // Maneja cualquier error que pueda ocurrir durante la solicitud
      }
    );
  }

}
