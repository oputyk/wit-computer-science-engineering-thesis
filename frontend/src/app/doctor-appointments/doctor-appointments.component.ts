import { Component, inject, } from '@angular/core';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import { DoctorApiService } from '../api/services/doctor-api.service';
import { CommonModule } from '@angular/common';
import {MatCardModule} from '@angular/material/card';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import { Appointment } from '../api/models/appointment';
import { UserApiService } from '../api/services/user-api.service';
@Component({
  selector: 'app-doctor-appointments',
  imports: [MatTableModule, CommonModule, MatCardModule, MatIconModule, MatButtonModule],
  templateUrl: './doctor-appointments.component.html',
  styleUrl: './doctor-appointments.component.css'
})
export class DoctorAppointmentsComponent {
  doctorApiService = inject(DoctorApiService)
  userApiService = inject(UserApiService)
  displayedColumnsAppointment: string[] = ['date','timeInMinutes','patient', 'patientPESEL','service','status', 'action'];
  dataSourceAppointments: MatTableDataSource<Appointment>;
  loggedInDoctorUuid: string;

  ngOnInit(): void {
    this.userApiService.getCurrentUserUuid().subscribe(doctorUuid => {
      this.loggedInDoctorUuid = doctorUuid.uuid;
      this.refreshTable();
    })
  }

  finishAppointment(appointment: Appointment) {
    if (appointment.status == 'CREATED') {
      this.doctorApiService.finishAppointment(this.loggedInDoctorUuid, appointment.uuid).subscribe(a => {
        this.refreshTable();
      })
    }
  }

  refreshTable(): void {
    this.doctorApiService.getAppointments(this.loggedInDoctorUuid).subscribe(data => {
      this.dataSourceAppointments = new MatTableDataSource<Appointment>(data);
    })
  }

  translateStatus(status: string): string {
    if (status == 'CREATED')
      return 'Utworzona'
    else if (status == 'CANCELED')
      return 'Anulowana'
    else if (status == 'FINISHED')
      return 'Zakończona'
    return ''
  }
}
