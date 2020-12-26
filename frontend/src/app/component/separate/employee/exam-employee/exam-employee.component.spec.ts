import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExamEmployeeComponent } from './exam-employee.component';

describe('ExamEmployeeComponent', () => {
  let component: ExamEmployeeComponent;
  let fixture: ComponentFixture<ExamEmployeeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ExamEmployeeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ExamEmployeeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
