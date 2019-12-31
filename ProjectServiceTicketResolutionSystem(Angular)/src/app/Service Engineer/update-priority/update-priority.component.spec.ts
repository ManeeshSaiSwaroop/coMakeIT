import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdatePriorityComponent } from './update-priority.component';

describe('UpdatePriorityComponent', () => {
  let component: UpdatePriorityComponent;
  let fixture: ComponentFixture<UpdatePriorityComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UpdatePriorityComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdatePriorityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
