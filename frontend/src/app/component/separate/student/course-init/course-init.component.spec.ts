import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CourseInitComponent } from './course-init.component';

describe('CourseInitComponent', () => {
  let component: CourseInitComponent;
  let fixture: ComponentFixture<CourseInitComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CourseInitComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CourseInitComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
