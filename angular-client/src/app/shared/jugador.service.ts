import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { Observable } from 'rxjs';
import { Nave } from '../model/nave';
import { Estrella } from '../model/estrella';
import { JugadorLogIn } from '../model/jugadorLogIn';
import { UsuarioDTO } from '../model/usuario-dto';
import { Jugador } from '../model/jugador';
import { PatchRolNave } from '../model/patch-rol-nave';
import { RegistroDTO } from '../model/registro-dto';

@Injectable({
  providedIn: 'root'
})
export class JugadorService {

  constructor(
    private http: HttpClient
  ) {
  }

  private headers = new HttpHeaders(
    { "Content-Type": "application/json" }
  )

  obtenerNaveJugador(id:number): Observable<Nave> {
    return this.http.get<Nave>(`${environment.serverUrl}/api/jugador/nave/${id}`)
  }

  iniciarSesion(jugador: JugadorLogIn): Observable<UsuarioDTO> {
    return this.http.post<UsuarioDTO>(`${environment.serverUrl}/api/jugador/login`, jugador, { headers: this.headers });
  }

  registro(jugador: RegistroDTO): Observable<UsuarioDTO> {
    return this.http.post<UsuarioDTO>(`${environment.serverUrl}/api/jugador/registro`, jugador, { headers: this.headers });
  }

  modificarRolYNave(patchRolNave: PatchRolNave, id:number): Observable<UsuarioDTO> {
    return this.http.patch<UsuarioDTO>(`${environment.serverUrl}/api/jugador/${id}/rol/nave`, patchRolNave, { headers: this.headers });
  }

  obtenerJugador(id:number): Observable<UsuarioDTO> {
    return this.http.get<UsuarioDTO>(`${environment.serverUrl}/api/jugador/${id}`);
  }


}
