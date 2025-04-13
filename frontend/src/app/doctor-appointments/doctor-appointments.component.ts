import { Component, inject, } from '@angular/core';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import { Doctor } from '../api/models/doctor';
import { DoctorApiService } from '../api/services/doctor-api.service';
import { CommonModule } from '@angular/common';
import {MatCardModule} from '@angular/material/card';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { DoctorFormComponent } from '../doctor-form/doctor-form.component';
import { DoctorRequest } from '../api/models/doctor-request';
import { Specialty } from '../api/models/specialty';
import { DoctorSpecialtyRequest } from '../api/models/doctor-specialty-request';
import { Appointment } from '../api/models/appointment';
@Component({
  selector: 'app-doctor-appointments',
  imports: [MatTableModule, CommonModule, MatCardModule, MatIconModule, MatButtonModule],
  templateUrl: './doctor-appointments.component.html',
  styleUrl: './doctor-appointments.component.css'
})
export class DoctorAppointmentsComponent {
  doctorApiService = inject(DoctorApiService)
  displayedColumnsAppointment: string[] = ['date','timeInMinutes','patient', 'patientPESEL','service','status'];
  dataSourceAppointments: MatTableDataSource<Appointment>;
  selectedDoctor: Doctor;

  ngOnInit(): void {
    this.doctorApiService.getCurrentDoctor().subscribe(doctor => {
      this.selectedDoctor = doctor;
      this.refreshTable();
    })
  }

  refreshTable(): void {
    this.doctorApiService.getAppointments(this.selectedDoctor.uuid).subscribe(data => {
      this.dataSourceAppointments = new MatTableDataSource<Appointment>(data);
    })
  }
}
