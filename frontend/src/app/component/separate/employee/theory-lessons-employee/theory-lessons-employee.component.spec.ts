import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TheoryLessonsEmployeeComponent } from './theory-lessons-employee.component';

describe('TheoryLessonsEmployeeComponent', () => {
  let component: TheoryLessonsEmployeeComponent;
  let fixture: ComponentFixture<TheoryLessonsEmployeeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TheoryLessonsEmployeeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TheoryLessonsEmployeeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
