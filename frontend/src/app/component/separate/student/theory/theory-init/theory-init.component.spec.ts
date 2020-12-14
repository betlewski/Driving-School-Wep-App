import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TheoryInitComponent } from './theory-init.component';

describe('TheoryInitComponent', () => {
  let component: TheoryInitComponent;
  let fixture: ComponentFixture<TheoryInitComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TheoryInitComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TheoryInitComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
