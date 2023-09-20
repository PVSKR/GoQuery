import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QuestionlandingComponent } from './questionlanding.component';

describe('QuestionlandingComponent', () => {
  let component: QuestionlandingComponent;
  let fixture: ComponentFixture<QuestionlandingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ QuestionlandingComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(QuestionlandingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
