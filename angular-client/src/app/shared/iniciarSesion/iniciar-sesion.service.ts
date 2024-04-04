import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment.development';
import { Observable } from 'rxjs';
import { UsuarioDTO } from '../../model/usuario-dto';
import { JugadorLogIn } from '../../model/jugadorLogIn';

@Injectable({
  providedIn: 'root'
})
export class IniciarSesionService {

  constructor(
    private http: HttpClient
  ) {
  }

  private headers = new HttpHeaders(
    { "Content-Type": "application/json" }
  )

  iniciarSesion(jugador: JugadorLogIn): Observable<UsuarioDTO> {
    return this.http.put<UsuarioDTO>(`${environment.serverUrl}/api/jugador/login`, jugador, { headers: this.headers });
  }
  
}
