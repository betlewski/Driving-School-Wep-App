import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OfficialAdminComponent } from './official-admin.component';

describe('OfficialAdminComponent', () => {
  let component: OfficialAdminComponent;
  let fixture: ComponentFixture<OfficialAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OfficialAdminComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OfficialAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
