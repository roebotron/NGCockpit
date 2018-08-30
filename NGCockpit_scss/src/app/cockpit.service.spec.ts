import { TestBed, inject } from '@angular/core/testing';

import { CockpitService } from './cockpit.service';

describe('CockpitService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [CockpitService]
    });
  });

  it('should be created', inject([CockpitService], (service: CockpitService) => {
    expect(service).toBeTruthy();
  }));
});
