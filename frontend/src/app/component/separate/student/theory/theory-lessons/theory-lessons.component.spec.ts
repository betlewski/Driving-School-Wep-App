import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TheoryLessonsComponent } from './theory-lessons.component';

describe('TheoryLessonsComponent', () => {
  let component: TheoryLessonsComponent;
  let fixture: ComponentFixture<TheoryLessonsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TheoryLessonsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TheoryLessonsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
