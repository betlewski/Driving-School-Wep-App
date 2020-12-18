import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonalDataAdminComponent } from './personal-data-admin.component';

describe('PersonalDataAdminComponent', () => {
  let component: PersonalDataAdminComponent;
  let fixture: ComponentFixture<PersonalDataAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PersonalDataAdminComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PersonalDataAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
