import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PatientAppointmentFormComponent } from './patient-appointment-form.component';

describe('PatientAppointmentFormComponent', () => {
  let component: PatientAppointmentFormComponent;
  let fixture: ComponentFixture<PatientAppointmentFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PatientAppointmentFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PatientAppointmentFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
