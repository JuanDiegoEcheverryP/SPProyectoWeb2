import { Component } from '@angular/core';
import { NaveService } from '../shared/nave.service';
import { Nave } from '../model/nave';
import { TipoNave } from '../model/tipoNave';
import { tipoNaveService } from '../shared/tipoNave.service';
import { UsuarioDTO } from '../model/usuario-dto';
import { Router } from '@angular/router';

@Component({
  selector: 'app-registrar-nueva-tripulacion',
  templateUrl: './registrar-nueva-tripulacion.component.html',
  styleUrl: './registrar-nueva-tripulacion.component.css'
})
export class RegistrarNuevaTripulacionComponent {
  usuarioDTO: UsuarioDTO= new UsuarioDTO(0,"","","",0)
  
  naves: TipoNave[] = [];
  actual: TipoNave = new TipoNave(1,"ss",1,1,"dd");
  indice:number = -1;

  nombreNave:String = "";

  usuarioString: string|null= sessionStorage.getItem("infoJugador");

  constructor(
    private tipoNaveService: tipoNaveService,
    private naveService: NaveService,
    private router: Router
  ) { }

  ngOnInit(): void {
    if(this.usuarioString){
      this.usuarioDTO= JSON.parse(this.usuarioString);
    }
    this.tipoNaveService.listarTipoNaves().subscribe(naves => {
      this.naves = naves
      this.actual = this.naves[0];
      this.indice = 0;
    });
  }

  onKey(event: any) { // without type info
    this.nombreNave += event.target.value + ' | ';
  }

  siguienteNave() {
    console.log(this.indice+1);
    
    if(this.indice == this.naves.length-1) {
      this.actual = this.naves[0];
      this.indice = 0;
    }
    else {
      this.actual = this.naves[this.indice+1];
      this.indice += 1;
    }
  }

  anteriorNave() {
    console.log(this.indice);
    
    if(this.indice == 0) {
      this.actual = this.naves[this.naves.length-1];
      this.indice = this.naves.length-1;
    }
    else {
      this.actual = this.naves[this.indice-1];
      this.indice -= 1;
    }
  }

  empezarJuego() {
    const nombreNave = (document.getElementById('nombreNave') as HTMLInputElement).value;
    if(nombreNave == "") {
      alert("Debe ingresar un nombre para la nave")
      return
    }
    this.naveService.registrarNuevaNave(this.usuarioDTO.id,nombreNave,this.indice+1).subscribe(
        usuario => {
        console.log('Respuesta del backend:', usuario);
        //this.shared.guardarInformacion(usuario)
        // Convertir el objeto usuario a una cadena JSON
        const usuarioString = JSON.stringify(usuario);
        
        // Guardar la cadena JSON en sessionStorage
        sessionStorage.setItem("infoJugador", usuarioString);
        
        this.router.navigate(['/menu']);
      },
      (error) => {
        console.error('Error al iniciar sesión:', error);
        // Maneja cualquier error que pueda ocurrir durante la solicitud
      }
    )
    //console.log(this.usuarioDTO.id, nombreNave,this.indice+1);
    
  }
}
