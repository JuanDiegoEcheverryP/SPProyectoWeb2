import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { JugadorService } from '../shared/jugador.service';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard {

  constructor(private auth: JugadorService,
    private router: Router) { }
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    const currentUrl = state.url;

    if (this.auth.isAuthenticated() && this.auth.role() != null) {
      const regex = /\/(registrarNuevaTripulacion|unirseTripulacion)\/\d+/; // Expresión regular para las rutas con parámetros dinámicos

      if (regex.test(currentUrl)) {
        this.router.navigateByUrl("/menu");
        return false;
      } else {
        return true;
      }
          
    } else if (this.auth.isAuthenticated() && this.auth.role() == null) {
      //si se autentico y no ha elegido su rol
      var usuarioString = sessionStorage.getItem("infoJugador");
      if (usuarioString) {
        var usuarioDTO = JSON.parse(usuarioString)
        this.router.navigate([`/unirseTripulacion/${usuarioDTO.id}`]);
      } else {
        this.router.navigateByUrl("/iniciarsesion");
      }
      return false;
    } else {

      this.router.navigateByUrl("/iniciarsesion");
      return false;
    }
  }
}
