import { Component } from '@angular/core';
import { JugadorService } from '../shared/jugador.service';
import { AvatarService } from '../shared/avatar.service';
import { Avatar } from '../model/avatar';
import { UsuarioDTO } from '../model/usuario-dto';
import { Jugador } from '../model/jugador';

@Component({
  selector: 'app-registro',
  templateUrl: './registro.component.html',
  styleUrls: ['./registro.component.css']
})
export class RegistroComponent {
  jugadorRegistro: Jugador = new Jugador(0, "", "", null, null);
  avatarUrl: string = "";
  nombreAvatar: string = "";
  avatares: Avatar[] = [];
  seleccionado: boolean = false;

  constructor(private registroService: JugadorService, private avatarService: AvatarService) {}

  ngOnInit() {
    this.imagenPredeterminada();
  }

  registro() 
  {
        this.registroService.registro(this.jugadorRegistro).subscribe(
          (usuario: UsuarioDTO) => {
            console.log('Respuesta del backend:', usuario);
            // Aquí puedes realizar cualquier acción con la respuesta del backend
          },
          (error) => {
            console.error('Error al registrarse:', error);
            // Maneja cualquier error que pueda ocurrir durante la solicitud
          }
        );
  }

  imagenPredeterminada() {
    this.avatarService.avatarId(1).subscribe(
      (avatar: Avatar) => {
        console.log('Respuesta del backend:', avatar);
        this.jugadorRegistro.avatar = avatar;
      },
      (error) => {
        console.error('Error al obtener el avatar predeterminado:', error);
      }
    );
  }

  obtenerAvatares() {
    this.avatarService.listarAvatares().subscribe(
      (avatares: Avatar[]) => {
        console.log('Respuesta del backend:', avatares);
        this.avatares = avatares;
        this.seleccionado = true;
      },
      (error) => {
        console.error('Error al obtener avatares:', error);
      }
    );
  }

  cerrar() {
    this.seleccionado = false;
  }

  asignarAvatar(avatar: Avatar) {
    this.seleccionado = false;
    this.jugadorRegistro.avatar = avatar;
  }
}
