import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { JugadorService } from '../shared/jugador.service';
@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private auth: JugadorService) { }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const token = this.auth.token();
    const url = request.url;
    console.log(request.url)
    // Lista de URLs que deben excluirse del interceptor
    const excludedUrls = [
      `${environment.serverUrl}/api/jugador/login`,
      `${environment.serverUrl}/api/jugador/registro`,
      `${environment.serverUrl}/api/avatar/list`,
      `${environment.serverUrl}/api/avatar/1`
    ];

    // Verificar si la URL está en la lista de exclusión
    if (excludedUrls.some(excludedUrl => url.includes(excludedUrl))) {
      return next.handle(request);
    }

    if (token == null) {
      return next.handle(request);
    } else {
      return next.handle(request.clone({
        headers: request.headers.set('Authorization', `Bearer ${token}`),
      }));
    }
  }
}

