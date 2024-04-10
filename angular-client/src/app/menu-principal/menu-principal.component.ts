import { Component } from '@angular/core';
import { JugadorService } from '../shared/jugador.service';
import { UsuarioDTO } from '../model/usuario-dto';
import { Router } from '@angular/router';
import { InfoGeneralUsuarioService } from '../shared/info-general-usuario.service';
import { NaveService } from '../shared/nave.service';
import { Jugador } from '../model/jugador';
import { NaveDTO } from '../model/nave-dto';

@Component({
  selector: 'app-menu-principal',
  templateUrl: './menu-principal.component.html',
  styleUrl: './menu-principal.component.css'
})
export class MenuPrincipalComponent {

  nombre:string=""
  rol:string | null=""
  usuarioDTO: UsuarioDTO= new UsuarioDTO(0,"","","",0)
  nombreNave:string=""
  tipo:string=""
  velocidad:number=0
  usuarioString: string|null= sessionStorage.getItem("infoJugador");


  constructor(private naveService: NaveService, private jugadorSevice: JugadorService, private router: Router, private shared: InfoGeneralUsuarioService) { }

  ngOnInit() {
    
    if(this.usuarioString)
    {
      this.usuarioDTO= JSON.parse(this.usuarioString);
    }
    //this.shared.leerInformacion();
    console.log("revisar id navee",this.usuarioDTO.idNave);
    
    this.obtenerJugador();
    this.obtenerInfoNave();
  }
  
  obtenerInfoNave() {
    this.naveService.obtenerNave(this.usuarioDTO.idNave).subscribe(
      (nave: NaveDTO) => {
        console.log('Respuesta del backend:', nave);
        this.nombreNave=nave.nombre
        this.tipo=nave.tipoNave
        this.velocidad=nave.velocidad
      },
      (error) => {
        console.error('Error al obtener nave:', error);
      }
    );
  }

  obtenerJugador()
  {
    this.jugadorSevice.obtenerJugador(this.usuarioDTO.id).subscribe(
      (jugador: UsuarioDTO) => {
        console.log('Respuesta del backend:', jugador);
        this.nombre=jugador.nombre
        this.rol=jugador.rol
      },
      (error) => {
        console.error('Error al obtener jugador:', error);
      }
    );
  }
  
}
