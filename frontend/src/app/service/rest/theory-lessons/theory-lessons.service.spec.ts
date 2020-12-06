import { TestBed } from '@angular/core/testing';

import { TheoryLessonsService } from './theory-lessons.service';

describe('TheoryLessonsService', () => {
  let service: TheoryLessonsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TheoryLessonsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
