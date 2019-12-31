import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AverageSeverityComponent } from './average-severity.component';

describe('AverageSeverityComponent', () => {
  let component: AverageSeverityComponent;
  let fixture: ComponentFixture<AverageSeverityComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AverageSeverityComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AverageSeverityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
