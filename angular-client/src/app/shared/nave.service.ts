import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { Observable } from 'rxjs';
import { Nave } from '../model/nave';

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
}
