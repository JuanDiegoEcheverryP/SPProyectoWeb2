import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
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
import { IniciarsesionComponent } from './iniciar-sesion/iniciarsesion.component';
import { BarraMenuComponent } from './components/barra-menu/barra-menu.component';
import { BarraEstadisticasComponent } from './components/barra-estadisticas/barra-estadisticas.component';
import { RegistroComponent } from './registro/registro.component';
import { UnirseATripulacionComponent } from './unirse-a-tripulacion/unirse-a-tripulacion.component';
import { MenuPrincipalComponent } from './menu-principal/menu-principal.component';
import { ComprarComponent } from './comprar/comprar.component';
import { VenderComponent } from './vender/vender.component';
import { InfoProductoComponent } from './components/info-producto/info-producto.component';
import { ErrorComponent } from './components/notifications/error/error.component';
import { SuccessComponent } from './components/notifications/success/success.component';
import { AuthInterceptor } from '../app/Interceptor/AuthInterceptor';

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
    IniciarsesionComponent,
    BarraMenuComponent,
    BarraEstadisticasComponent,
    RegistroComponent,
    UnirseATripulacionComponent,
    MenuPrincipalComponent,
    ComprarComponent,
    VenderComponent,
    InfoProductoComponent,
    ErrorComponent,
    SuccessComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
  ],
  providers: [{ provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },],
  bootstrap: [AppComponent]
})
export class AppModule { }
