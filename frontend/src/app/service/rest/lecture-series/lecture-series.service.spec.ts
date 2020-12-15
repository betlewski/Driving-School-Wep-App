import { TestBed } from '@angular/core/testing';

import { LectureSeriesService } from './lecture-series.service';

describe('LectureSeriesService', () => {
  let service: LectureSeriesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LectureSeriesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
