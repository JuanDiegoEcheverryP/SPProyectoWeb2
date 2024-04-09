import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { Observable } from 'rxjs';
import { Camino } from '../model/camino';
import { EstrellaDTO } from '../model/estrellaDTO';

@Injectable({
  providedIn: 'root'
})
export class CaminoService {

  constructor(
    private http: HttpClient
  ) {
  }

  private headers = new HttpHeaders(
    { "Content-Type": "application/json" }
  )

 obtenerCaminosPorEstrellaId(id:number): Observable<EstrellaDTO[]> {
    return this.http.get<EstrellaDTO[]>(`${environment.serverUrl}/api/camino/estrellasConectadas/${id}`)
  }

  obtenerCaminoPorEstrellas(idInicio:number,idFinal:number): Observable<Camino> {
    return this.http.get<Camino>(`${environment.serverUrl}/api/camino/inicioFinal/${idInicio}/${idFinal}`)
  }
}
