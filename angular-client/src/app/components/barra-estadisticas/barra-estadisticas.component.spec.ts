import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BarraEstadisticasComponent } from './barra-estadisticas.component';

describe('BarraEstadisticasComponent', () => {
  let component: BarraEstadisticasComponent;
  let fixture: ComponentFixture<BarraEstadisticasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BarraEstadisticasComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BarraEstadisticasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
