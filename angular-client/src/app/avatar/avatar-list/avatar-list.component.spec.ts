import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AvatarListComponent } from './avatar-list.component';

describe('AvatarListComponent', () => {
  let component: AvatarListComponent;
  let fixture: ComponentFixture<AvatarListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AvatarListComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AvatarListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
