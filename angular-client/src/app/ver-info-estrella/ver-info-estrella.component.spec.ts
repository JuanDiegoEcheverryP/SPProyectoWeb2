import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VerInfoEstrellaComponent } from './ver-info-estrella.component';

describe('VerInfoEstrellaComponent', () => {
  let component: VerInfoEstrellaComponent;
  let fixture: ComponentFixture<VerInfoEstrellaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [VerInfoEstrellaComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(VerInfoEstrellaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
