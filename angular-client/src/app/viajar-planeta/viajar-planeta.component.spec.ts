import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViajarPlanetaComponent } from './viajar-planeta.component';

describe('ViajarPlanetaComponent', () => {
  let component: ViajarPlanetaComponent;
  let fixture: ComponentFixture<ViajarPlanetaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ViajarPlanetaComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ViajarPlanetaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
