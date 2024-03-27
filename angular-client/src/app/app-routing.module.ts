import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AvatarListComponent } from './avatar/avatar-list/avatar-list.component';

const routes: Routes = [
  { path: 'avatar/list', component: AvatarListComponent },
  { path: '', pathMatch: 'full', redirectTo: 'avatar/list' },
  {path: '**', component: AvatarListComponent}
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
