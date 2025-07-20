import { Component, inject, } from '@angular/core';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import { CommonModule } from '@angular/common';
import {MatCardModule} from '@angular/material/card';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import { Appointment } from '../api/models/appointment';
import { UserApiService } from '../api/services/user-api.service';
import { PatientApiService } from '../api/services/patient-api.service';
import { MatDialog } from '@angular/material/dialog';
import { PatientAppointmentFormComponent } from '../patient-appointment-form/patient-appointment-form.component';
import { AppointmentRequest } from '../api/models/appointment-request';

@Component({
  selector: 'app-patient-appointments',
  imports: [MatTableModule, CommonModule, MatCardModule, MatIconModule, MatButtonModule],
  templateUrl: './patient-appointments.component.html',
  styleUrl: './patient-appointments.component.css'
})
export class PatientAppointmentsComponent {
  patientApiService = inject(PatientApiService)
  userApiService = inject(UserApiService)
  readonly dialog = inject(MatDialog);
  displayedColumnsAppointment: string[] = ['date','timeInMinutes','doctor','service','status', 'action'];
  dataSourceAppointments: MatTableDataSource<Appointment>;
  loggedInPatientUuid: string;

  ngOnInit(): void {
    this.userApiService.getCurrentUserUuid().subscribe(patientUuid => {
      this.loggedInPatientUuid = patientUuid.uuid;
      this.refreshTable();
    })
  }
  cancelAppointment(appointment: Appointment) {
    if (appointment.status == 'CREATED') {
      this.patientApiService.cancelAppointment(this.loggedInPatientUuid, appointment.uuid).subscribe(a => {
        this.refreshTable();
      })
    }
  }
  create(): void {
    const dialogRef = this.dialog.open(PatientAppointmentFormComponent, {
      data: {},
      height: '400px',
      width: '600px',
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result !== undefined) {
        this.patientApiService.createAppointment(this.loggedInPatientUuid, result as AppointmentRequest).subscribe(res => {
          this.refreshTable();
        })
      }
    });
  }
  refreshTable(): void {
    this.patientApiService.getAppointments(this.loggedInPatientUuid).subscribe(data => {
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
