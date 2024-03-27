import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ComerciarComponent } from './comerciar.component';

describe('ComerciarComponent', () => {
  let component: ComerciarComponent;
  let fixture: ComponentFixture<ComerciarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ComerciarComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ComerciarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
