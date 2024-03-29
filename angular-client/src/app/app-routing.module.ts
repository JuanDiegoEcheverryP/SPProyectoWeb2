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

const routes: Routes = [
  { path: 'avatar/list', component: AvatarListComponent },
  { path: 'registrarNuevaTripulacion', component: RegistrarNuevaTripulacionComponent },
  { path: 'comerciar', component: ComerciarComponent },
  { path: 'bodega', component: BodegaComponent },
  { path: 'visualizarMapa', component: VisualizarMapaComponent },
  { path: 'verInfoEstrella', component:  VerInfoEstrellaComponent},
  { path: 'viajarPlaneta/:idEstrella', component:  ViajarPlanetaComponent},
  { path: 'viajarEstrella/:idEstrella', component:  ViajarEstrellaComponent},
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
