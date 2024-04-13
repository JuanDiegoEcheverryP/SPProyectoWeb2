import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment.development';
import { CompraVentaDTO } from '../model/compra-venta-dto';
import { RespuestaTransaccionDTO } from '../model/respuesta-transaccion-dto';

@Injectable({
  providedIn: 'root'
})
export class TransaccionService {
  constructor(
    private http: HttpClient
  ) {
  }

  private headers = new HttpHeaders(
    { "Content-Type": "application/json" }
  )


  compra(compra: CompraVentaDTO): Observable<RespuestaTransaccionDTO> {
    return this.http.put<RespuestaTransaccionDTO>(`${environment.serverUrl}/api/transaccion/compra`, compra, { headers: this.headers });
  }

  venta(venta: CompraVentaDTO): Observable<RespuestaTransaccionDTO> {
    return this.http.put<RespuestaTransaccionDTO>(`${environment.serverUrl}/api/transaccion/venta`, venta, { headers: this.headers });
  }
}
