import { TestBed } from '@angular/core/testing';

import { InternalExamService } from './internal-exam.service';

describe('InternalExamService', () => {
  let service: InternalExamService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InternalExamService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
