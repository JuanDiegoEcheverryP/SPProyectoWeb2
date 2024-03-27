import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { Observable } from 'rxjs';
import { Nave } from '../model/nave';
import { TipoNave } from '../model/tipoNave';

@Injectable({
  providedIn: 'root'
})
export class tipoNaveService {

  constructor(
    private http: HttpClient
  ) {
  }

  private headers = new HttpHeaders(
    { "Content-Type": "application/json" }
  )

  listarTipoNaves(): Observable<TipoNave[]> {
    return this.http.get<TipoNave[]>(`${environment.serverUrl}/api/tipoNave/list`)
  }
}
