import { TestBed } from '@angular/core/testing';

import { DrivingLessonService } from './driving-lesson.service';

describe('DrivingLessonService', () => {
  let service: DrivingLessonService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DrivingLessonService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
