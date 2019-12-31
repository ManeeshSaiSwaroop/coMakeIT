import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiceEngineerComponent } from './service-engineer.component';

describe('ServiceEngineerComponent', () => {
  let component: ServiceEngineerComponent;
  let fixture: ComponentFixture<ServiceEngineerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ServiceEngineerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ServiceEngineerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
