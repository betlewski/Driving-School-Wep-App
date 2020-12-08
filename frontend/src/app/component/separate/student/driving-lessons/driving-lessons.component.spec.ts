import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DrivingLessonsComponent } from './driving-lessons.component';

describe('DrivingLessonsComponent', () => {
  let component: DrivingLessonsComponent;
  let fixture: ComponentFixture<DrivingLessonsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DrivingLessonsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DrivingLessonsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
