import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { Observable } from 'rxjs';
import { Nave } from '../model/nave';
import { Estrella } from '../model/estrella';

@Injectable({
  providedIn: 'root'
})
export class EstrellaService {

  constructor(
    private http: HttpClient
  ) {
  }

  private headers = new HttpHeaders(
    { "Content-Type": "application/json" }
  )

  obtenerEstrellaPorId(id:number): Observable<Estrella> {
    return this.http.get<Estrella>(`${environment.serverUrl}/api/estrella/${id}`)
  }
}
