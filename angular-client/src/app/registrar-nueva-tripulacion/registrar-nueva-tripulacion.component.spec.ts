import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistrarNuevaTripulacionComponent } from './registrar-nueva-tripulacion.component';

describe('RegistrarNuevaTripulacionComponent', () => {
  let component: RegistrarNuevaTripulacionComponent;
  let fixture: ComponentFixture<RegistrarNuevaTripulacionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RegistrarNuevaTripulacionComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RegistrarNuevaTripulacionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
