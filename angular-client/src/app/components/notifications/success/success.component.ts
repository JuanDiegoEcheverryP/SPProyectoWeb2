import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-success',
  templateUrl: './success.component.html',
  styleUrl: './success.component.css'
})
export class SuccessComponent {

  @Input() informacionRecibir: string=""

  @Output() cerrar = new EventEmitter<boolean>();

  cerrarDiv()
  {
    this.cerrar.emit(false)
  }
}
