import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { Observable } from 'rxjs';
import { Nave } from '../model/nave';
import { Estrella } from '../model/estrella';

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
}
