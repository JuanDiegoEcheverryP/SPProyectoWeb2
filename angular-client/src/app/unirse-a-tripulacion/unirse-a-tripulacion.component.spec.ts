import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UnirseATripulacionComponent } from './unirse-a-tripulacion.component';

describe('UnirseATripulacionComponent', () => {
  let component: UnirseATripulacionComponent;
  let fixture: ComponentFixture<UnirseATripulacionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [UnirseATripulacionComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(UnirseATripulacionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
