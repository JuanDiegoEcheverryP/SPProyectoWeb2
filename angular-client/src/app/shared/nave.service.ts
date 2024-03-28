import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { Observable } from 'rxjs';
import { Nave } from '../model/nave';
import { Planeta } from '../model/planeta';
import { Estrella } from '../model/estrella';

@Injectable({
  providedIn: 'root'
})
export class NaveService {

  constructor(
    private http: HttpClient
  ) {
  }

  private headers = new HttpHeaders(
    { "Content-Type": "application/json" }
  )

  listarNaves(): Observable<Nave[]> {
    return this.http.get<Nave[]>(`${environment.serverUrl}/api/nave/list`)
  }

  listarNavesPorEstrella(id:number): Observable<Nave[]> {
    return this.http.get<Nave[]>(`${environment.serverUrl}/api/estrella/naves/${id}`)
  }

  obtenerPlanetaPorNaveId(id:number): Observable<Planeta> {
    return this.http.get<Planeta>(`${environment.serverUrl}/api/nave/obtenerPlaneta/${id}`)
  }

  obtenerEstrellaPorNaveId(id:number): Observable<Estrella> {
    return this.http.get<Estrella>(`${environment.serverUrl}/api/nave/obtenerEstrella/${id}`)
  }
}
