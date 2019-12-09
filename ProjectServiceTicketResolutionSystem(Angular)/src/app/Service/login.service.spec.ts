import { TestBed } from '@angular/core/testing';

import { CallRESTService } from './login.service';

describe('CallRESTService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CallRESTService = TestBed.get(CallRESTService);
    expect(service).toBeTruthy();
  });
});
