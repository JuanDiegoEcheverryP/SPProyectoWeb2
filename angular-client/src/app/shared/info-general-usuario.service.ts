import { Injectable } from '@angular/core';
import { UsuarioDTO } from '../model/usuario-dto';

@Injectable({
  providedIn: 'root'
})
export class InfoGeneralUsuarioService {
  usuarioDTO:UsuarioDTO= new UsuarioDTO(1,"","","",1)

  constructor() { }

  guardarInformacion(usuarioDTO:UsuarioDTO)
  {
    this.usuarioDTO=usuarioDTO
  }

  leerInformacion()
  {
    return this.usuarioDTO
  }
}
