import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViajarEstrellaComponent } from './viajar-estrella.component';

describe('ViajarEstrellaComponent', () => {
  let component: ViajarEstrellaComponent;
  let fixture: ComponentFixture<ViajarEstrellaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ViajarEstrellaComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ViajarEstrellaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
