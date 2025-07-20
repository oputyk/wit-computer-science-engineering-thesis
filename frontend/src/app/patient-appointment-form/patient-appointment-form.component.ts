import {ChangeDetectionStrategy, Component, inject, model, signal} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {MatButtonModule} from '@angular/material/button';
import {
  MAT_DIALOG_DATA,
  MatDialog,
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle,
} from '@angular/material/dialog';
import {MatSelectModule} from '@angular/material/select';
import {MatCardModule} from '@angular/material/card';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatInputModule} from '@angular/material/input';
import {provideNativeDateAdapter} from '@angular/material/core';
import {MatTimepickerModule} from '@angular/material/timepicker';
import { AppointmentRequest } from '../api/models/appointment-request';
import { PatientApiService } from '../api/services/patient-api.service';
import { Observable } from 'rxjs';
import { AvailableAppointmentTime } from '../api/models/available-appointment-time';
import { UserApiService } from '../api/services/user-api.service';
import { DoctorApiService } from '../api/services/doctor-api.service';
import { ServiceApiService } from '../api/services/service-api.service';
import { Service } from '../api/models/service';
import { Doctor } from '../api/models/doctor';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-patient-appointment-form',
  imports: [
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    MatButtonModule,
    MatDialogTitle,
    MatDialogContent,
    MatDialogActions,
    MatDialogClose,
    MatCardModule,
    MatSelectModule,
    MatDatepickerModule,
    MatTimepickerModule,
    NgIf,
  ],
  providers: [provideNativeDateAdapter()],
  templateUrl: './patient-appointment-form.component.html',
  styleUrl: './patient-appointment-form.component.css'
})
export class PatientAppointmentFormComponent {
  readonly dialogRef = inject(MatDialogRef<PatientAppointmentFormComponent>);
  readonly appointmentRequest = structuredClone(inject<AppointmentRequest>(MAT_DIALOG_DATA));
  patientApiService = inject(PatientApiService)
  userApiService = inject(UserApiService)
  doctorApiService = inject(DoctorApiService)
  serviceApiService = inject(ServiceApiService)
  availableAppointmentTimes: AvailableAppointmentTime[] | null;
  services: Service[];
  doctors: Doctor[];
  loggedInPatientUuid: string;
  service: Service;
  doctor: Doctor;
  date: Date;
  availableAppointmentTime: Date;

  ngOnInit(): void {
    this.userApiService.getCurrentUserUuid().subscribe(patientUuid => {
      this.loggedInPatientUuid = patientUuid.uuid;
      this.serviceApiService.getAllServices().subscribe(services => {
        this.services = services;
      })
    });
  }
  updateDoctors(service: Service): void {
    this.doctorApiService.getDoctorsBySpecialty(service.specialty.uuid).subscribe(doctors => {
      this.doctors = doctors;
      this.availableAppointmentTimes = null;
    });
  }
  updateAvailableAppointmentTimes(doctor: Doctor): void {
    this.patientApiService.getAvailableAppointmentTimes(this.loggedInPatientUuid, doctor.uuid, this.service.uuid, this.date).subscribe(availableAppointmentTimes => {
      this.availableAppointmentTimes = availableAppointmentTimes;
    });
  }
  updateDate(date: Date): void {
    this.patientApiService.getAvailableAppointmentTimes(this.loggedInPatientUuid, this.doctor.uuid, this.service.uuid, date).subscribe(availableAppointmentTimes => {
      this.availableAppointmentTimes = availableAppointmentTimes;
    });
  }
  cancel(): void {
    this.dialogRef.close();
  }
  save(): void {
    this.dialogRef.close({
      doctorUuid: this.doctor.uuid,
      serviceUuid: this.service.uuid,
      dateTimeFrom: this.availableAppointmentTime
    } as AppointmentRequest);
  }
}
