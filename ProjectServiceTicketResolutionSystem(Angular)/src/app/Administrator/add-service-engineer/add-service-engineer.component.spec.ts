import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddServiceEngineerComponent } from './add-service-engineer.component';

describe('AddServiceEngineerComponent', () => {
  let component: AddServiceEngineerComponent;
  let fixture: ComponentFixture<AddServiceEngineerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddServiceEngineerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddServiceEngineerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
