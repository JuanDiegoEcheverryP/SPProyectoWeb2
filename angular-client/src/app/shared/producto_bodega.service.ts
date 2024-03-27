import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { Observable } from 'rxjs';
import { Nave } from '../model/nave';
import { ProductoBodega } from '../model/producto_bodega';

@Injectable({
  providedIn: 'root'
})
export class ProductoBodegaService {

  constructor(
    private http: HttpClient
  ) {
  }

  private headers = new HttpHeaders(
    { "Content-Type": "application/json" }
  )

  listarProductosPorNave(id:number): Observable<ProductoBodega[]> {
    return this.http.get<ProductoBodega[]>(`${environment.serverUrl}/api/bodega/${id}`)
  }
}
