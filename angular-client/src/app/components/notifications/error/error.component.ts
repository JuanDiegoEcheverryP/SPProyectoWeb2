import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrl: './error.component.css'
})
export class ErrorComponent {

  @Input() informacionRecibir: string=""

  @Output() cerrar = new EventEmitter<boolean>();

  cerrarDiv()
  {
    this.cerrar.emit(false)
  }
}
