import { Component } from '@angular/core';
import { JugadorService } from '../shared/jugador.service';
import { AvatarService } from '../shared/avatar.service';
import { Avatar } from '../model/avatar';
import { UsuarioDTO } from '../model/usuario-dto';
import { Jugador } from '../model/jugador';
import { Router } from '@angular/router';
import { InfoGeneralUsuarioService } from '../shared/info-general-usuario.service';
import { RegistroDTO } from '../model/registro-dto';


@Component({
  selector: 'app-registro',
  templateUrl: './registro.component.html',
  styleUrls: ['./registro.component.css']
})
export class RegistroComponent {
  jugadorRegistro: RegistroDTO = new RegistroDTO(0, "", "", "", null, null);
  avatarUrl: string = "";
  nombreAvatar: string = "";
  avatares: Avatar[] = [];
  seleccionado: boolean = false;
  error: boolean = false;
  accionSeleccionada: string="";
  notificacionText:string=""

  constructor(private registroService: JugadorService, private avatarService: AvatarService,private router: Router, private shared:InfoGeneralUsuarioService) {}

  ngOnInit() {
    this.imagenPredeterminada();
  }

  registro() 
  {
    if(this.jugadorRegistro.nombre!="" && this.jugadorRegistro.contrasena2!="" && this.jugadorRegistro.contrasena!="")
    {
      this.registroService.registro(this.jugadorRegistro).subscribe(
        (usuario: UsuarioDTO) => {
          console.log('Respuesta del backend:', usuario);
          //this.shared.guardarInformacion(usuario)
          // Convertir el objeto usuario a una cadena JSON
          const usuarioString = JSON.stringify(usuario);
          
          // Guardar la cadena JSON en sessionStorage
          sessionStorage.setItem("infoJugador", usuarioString);
          sessionStorage.setItem("token", usuario.token);
          if(this.accionSeleccionada=="crear")
          {
            this.router.navigate([`/registrarNuevaTripulacion/${usuario.id}`]);
          }
          else if (this.accionSeleccionada=="unirse")
          {
            this.router.navigate([`/unirseTripulacion/${usuario.id}`]);
          }
          // Aquí puedes realizar cualquier acción con la respuesta del backend
        },
        (error) => {
          console.error('Error al registrarse:', error);
          this.notificacionText=error.error;
          this.error=true
        }
      );
    }
    else
    {
      //notificacion de que hay campos vacios
      this.notificacionText="Todos los campos deven estar llenos"
      this.error=true
    }
        
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
    console.log(this.jugadorRegistro.avatar);
    
  }

  AceptarError()
  {
    this.error=false
  }
}
