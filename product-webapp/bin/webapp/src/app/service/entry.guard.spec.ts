import { TestBed } from '@angular/core/testing';

import { EntryGuard } from './entry.guard';

describe('EntryGuard', () => {
  let guard: EntryGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(EntryGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
