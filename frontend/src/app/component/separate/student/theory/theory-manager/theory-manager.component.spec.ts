import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TheoryManagerComponent } from './theory-manager.component';

describe('TheoryManagerComponent', () => {
  let component: TheoryManagerComponent;
  let fixture: ComponentFixture<TheoryManagerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TheoryManagerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TheoryManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
