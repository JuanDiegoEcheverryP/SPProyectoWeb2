import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AvatarListComponent } from './avatar/avatar-list/avatar-list.component';
import { InicioComponent } from './inicio/inicio.component';
import { RegistrarNuevaTripulacionComponent } from './registrar-nueva-tripulacion/registrar-nueva-tripulacion.component';
import { ComerciarComponent } from './comerciar/comerciar.component';
import { BodegaComponent } from './bodega/bodega.component';
import { VisualizarMapaComponent } from './visualizar-mapa/visualizar-mapa.component';
import { VerInfoEstrellaComponent } from './ver-info-estrella/ver-info-estrella.component';
import { ViajarPlanetaComponent } from './viajar-planeta/viajar-planeta.component';
import { ViajarEstrellaComponent } from './viajar-estrella/viajar-estrella.component';
import { IniciarsesionComponent } from './iniciar-sesion/iniciarsesion.component';
import { RegistroComponent } from './registro/registro.component';
import { UnirseATripulacionComponent} from './unirse-a-tripulacion/unirse-a-tripulacion.component';
import { ComprarComponent } from './comprar/comprar.component';
import { MenuPrincipalComponent } from './menu-principal/menu-principal.component';
import { VenderComponent } from './vender/vender.component';
import { BarraMenuComponent } from './components/barra-menu/barra-menu.component';

const routes: Routes = [
  { path: 'avatar/list', component: AvatarListComponent },
  { path: 'registrarNuevaTripulacion', component: RegistrarNuevaTripulacionComponent },
  { path: 'comerciar/:idJugador', component: ComerciarComponent },
  { path: 'bodega/:idJugador', component: BodegaComponent },
  { path: 'visualizarMapa/:idJugador', component: VisualizarMapaComponent },
  { path: 'verInfoEstrella/:idJugador', component:  VerInfoEstrellaComponent},
  { path: 'viajarEstrella/:idJugador/:idEstrella', component:  ViajarEstrellaComponent},
  { path: 'viajarPlaneta/:idJugador/:idEstrella', component:  ViajarPlanetaComponent},
  { path: 'iniciarsesion', component:  IniciarsesionComponent},
  { path: 'registro', component:  RegistroComponent},
  { path: 'unirseTripulacion', component:  UnirseATripulacionComponent},
  { path: 'menu', component:  MenuPrincipalComponent},
  { path: 'comprar/:idProducto', component:  ComprarComponent},
  { path: 'vender/:idProducto', component:  VenderComponent},
  {path: '**', component: InicioComponent} //Inicio
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes,
    {
      bindToComponentInputs: true, // Para poder usar @Input en rutas https://angular.io/guide/router
      onSameUrlNavigation: 'reload' // https://stackoverflow.com/a/52512361
    })], 
  exports: [RouterModule]
})
export class AppRoutingModule { }
