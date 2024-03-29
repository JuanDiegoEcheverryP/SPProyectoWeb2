import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { AvatarListComponent } from './avatar/avatar-list/avatar-list.component';
import { InicioComponent } from './inicio/inicio.component';
import { RegistrarNuevaTripulacionComponent } from './registrar-nueva-tripulacion/registrar-nueva-tripulacion.component';
import { ComerciarComponent } from './comerciar/comerciar.component';
import { BodegaComponent } from './bodega/bodega.component';
import { VisualizarMapaComponent } from './visualizar-mapa/visualizar-mapa.component';
import { VerInfoEstrellaComponent } from './ver-info-estrella/ver-info-estrella.component';
import { ViajarPlanetaComponent } from './viajar-planeta/viajar-planeta.component';
import { ViajarEstrellaComponent } from './viajar-estrella/viajar-estrella.component';

@NgModule({
  declarations: [
    AppComponent,
    InicioComponent,
    AvatarListComponent,
    InicioComponent,
    RegistrarNuevaTripulacionComponent,
    ComerciarComponent,
    BodegaComponent,
    VisualizarMapaComponent,
    VerInfoEstrellaComponent,
    ViajarPlanetaComponent,
    ViajarEstrellaComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
