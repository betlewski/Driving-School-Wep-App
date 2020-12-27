import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LectureSeriesComponent } from './lecture-series.component';

describe('LectureSeriesComponent', () => {
  let component: LectureSeriesComponent;
  let fixture: ComponentFixture<LectureSeriesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LectureSeriesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LectureSeriesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
