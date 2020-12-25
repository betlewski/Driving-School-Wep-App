import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DrivingLessonsEmployeeComponent } from './driving-lessons-employee.component';

describe('DrivingLessonsEmployeeComponent', () => {
  let component: DrivingLessonsEmployeeComponent;
  let fixture: ComponentFixture<DrivingLessonsEmployeeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DrivingLessonsEmployeeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DrivingLessonsEmployeeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
