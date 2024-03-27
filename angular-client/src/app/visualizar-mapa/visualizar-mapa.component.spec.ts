import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VisualizarMapaComponent } from './visualizar-mapa.component';

describe('VisualizarMapaComponent', () => {
  let component: VisualizarMapaComponent;
  let fixture: ComponentFixture<VisualizarMapaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [VisualizarMapaComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(VisualizarMapaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
