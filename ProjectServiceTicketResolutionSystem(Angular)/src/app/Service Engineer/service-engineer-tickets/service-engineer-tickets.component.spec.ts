import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiceEngineerTicketsComponent } from './service-engineer-tickets.component';

describe('ServiceEngineerTicketsComponent', () => {
  let component: ServiceEngineerTicketsComponent;
  let fixture: ComponentFixture<ServiceEngineerTicketsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ServiceEngineerTicketsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ServiceEngineerTicketsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
