import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { JugadorService } from '../shared/jugador.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard  {

  constructor(private auth: JugadorService,
    private router: Router) { }
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    if (this.auth.isAuthenticated() && this.auth.role()!=null) {
      return true;
    }else  if (this.auth.isAuthenticated() && this.auth.role()==null) {
      
      var usuarioString= sessionStorage.getItem("infoJugador");
      if(usuarioString)
      {
         var usuarioDTO= JSON.parse(usuarioString)
         this.router.navigate([`/unirseTripulacion/${usuarioDTO.id}`]);
      }
      else
      {
        this.router.navigateByUrl("/iniciarsesion");
      }
      return false;
    }
     else {
      this.router.navigateByUrl("/iniciarsesion");
      return false;
    }
  }

}
