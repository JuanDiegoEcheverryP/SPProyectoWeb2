import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AvatarListComponent } from './avatar/avatar-list/avatar-list.component';
import { InicioComponent } from './inicio/inicio.component';

const routes: Routes = [
  { path: 'avatar/list', component: AvatarListComponent },
  {path: '**', component: InicioComponent}
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
