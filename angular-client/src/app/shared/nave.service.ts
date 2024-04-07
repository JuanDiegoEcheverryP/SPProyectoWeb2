import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { Observable } from 'rxjs';
import { Nave } from '../model/nave';
import { Planeta } from '../model/planeta';
import { Estrella } from '../model/estrella';
import { TripulacionDTO } from '../model/tripulacion-dto';
import { NaveDTO } from '../model/nave-dto';

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

  listaTripulaciones(): Observable<TripulacionDTO[]> {
    return this.http.get<TripulacionDTO[]>(`${environment.serverUrl}/api/nave/tripulaciones`)
  }

  obtenerNave(id:number): Observable<NaveDTO> {
    return this.http.get<NaveDTO>(`${environment.serverUrl}/api/nave/ver/${id}`)
  }

}

