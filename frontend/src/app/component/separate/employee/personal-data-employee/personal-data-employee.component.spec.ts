import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonalDataEmployeeComponent } from './personal-data-employee.component';

describe('PersonalDataEmployeeComponent', () => {
  let component: PersonalDataEmployeeComponent;
  let fixture: ComponentFixture<PersonalDataEmployeeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PersonalDataEmployeeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PersonalDataEmployeeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
