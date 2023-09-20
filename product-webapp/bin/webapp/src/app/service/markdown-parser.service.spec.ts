/* tslint:disable:no-unused-variable */

import { addProviders, async, inject } from '@angular/core/testing';
import { MarkdownParserService } from './markdown-parser.service';

describe('Service: MarkdownParser', () => {
  beforeEach(() => {
    addProviders([MarkdownParserService]);
  });

  it('should ...',
    inject([MarkdownParserService],
      (service: MarkdownParserService) => {
        expect(service).toBeTruthy();
      }));
});
