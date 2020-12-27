import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LectureSeriesInitComponent } from './lecture-series-init.component';

describe('LectureSeriesInitComponent', () => {
  let component: LectureSeriesInitComponent;
  let fixture: ComponentFixture<LectureSeriesInitComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LectureSeriesInitComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LectureSeriesInitComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
