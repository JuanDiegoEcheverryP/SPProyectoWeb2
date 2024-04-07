import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { Observable } from 'rxjs';
import { Avatar } from '../model/avatar';
import { Producto } from '../model/producto';
import { ProductoDTO } from '../model/productoDTO';

@Injectable({
  providedIn: 'root'
})
export class StockProductoService {

  constructor(
    private http: HttpClient
  ) {
  }

  private headers = new HttpHeaders(
    { "Content-Type": "application/json" }
  )

  listarProductosPlaneta(id:number): Observable<ProductoDTO[]> {
    return this.http.get<ProductoDTO[]>(`${environment.serverUrl}/api/stock/planeta/${id}`)
  }

  recuperarProductosPorStock(idProducto:number, idPlaneta:number): Observable<ProductoDTO> {
    return this.http.get<ProductoDTO>(`${environment.serverUrl}/api/stock/planeta/${idPlaneta}/producto/${idProducto}`)
  }
}
