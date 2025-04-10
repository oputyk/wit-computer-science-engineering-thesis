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
  selector: 'app-doctors',
  imports: [MatTableModule, CommonModule, MatCardModule, MatIconModule, MatButtonModule],
  templateUrl: './doctors.component.html',
  styleUrl: './doctors.component.css'
})
export class DoctorsComponent {
  doctorApiService = inject(DoctorApiService)
  displayedColumns: string[] = ['email','name','surname','pesel','specialties', 'action'];
  displayedColumnsAppointment: string[] = ['date','timeInMinutes','patient', 'patientPESEL','service','status'];
  dataSource: MatTableDataSource<Doctor>;
  dataSourceAppointments: MatTableDataSource<Appointment>;
  readonly dialog = inject(MatDialog);
  selectedDoctor: Doctor | null = null;

  ngOnInit(): void {
    this.refreshTable();
  }

  create(): void {
    const dialogRef = this.dialog.open(DoctorFormComponent, {
      data: {},
      height: '500px',
      width: '600px',
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result !== undefined) {
        this.doctorApiService.createDoctor(result.doctor as DoctorRequest).subscribe(res => {
          result.specialties.forEach((s: Specialty) => {
            this.doctorApiService.createDoctorSpecialty(result.doctor.uuid, {specialtyUuid: s.uuid} as DoctorSpecialtyRequest).subscribe(a => {})
          })
          this.refreshTable();
        })
      }
    });
  }

  edit(doctor: Doctor): void {
    const dialogRef = this.dialog.open(DoctorFormComponent, {
      data: doctor,
      height: '500px',
      width: '600px',
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result !== undefined) {
        let resultUuids = result.specialties.map((s: Specialty) => s.uuid)
        let oldUuids = doctor.specialties.map((s: Specialty) => s.uuid)
        let specialtiesToBeCreated = result.specialties.filter((s: Specialty) => !oldUuids.includes(s.uuid))
        let specialtiesToBeRemoved = doctor.specialties.filter((s: Specialty) => !resultUuids.includes(s.uuid))
        specialtiesToBeCreated.forEach((s: Specialty) => {
          this.doctorApiService.createDoctorSpecialty(doctor.uuid, {specialtyUuid: s.uuid} as DoctorSpecialtyRequest).subscribe(a => {})
        })
        specialtiesToBeRemoved.forEach((s: Specialty) => {
          this.doctorApiService.deleteDoctorSpecialty(doctor.uuid, s.uuid).subscribe(a => {})
        })
        this.doctorApiService.updateDoctor(doctor.uuid, result as DoctorRequest).subscribe(res => {
          this.refreshTable();
        })
      }
    });
  }

  delete(doctor: Doctor): void {
    this.doctorApiService.deleteDoctor(doctor.uuid).subscribe(res => {
      this.refreshTable();
    })
  }

  refreshTable(): void {
    this.doctorApiService.getAllDoctors().subscribe(data => {
      this.dataSource = new MatTableDataSource<Doctor>(data);
    })
  }

  getNames(specialties: Specialty[]): string {
    return specialties.map(s => s.name).join(', ')
  }

  selectDoctor(doctor: Doctor): void {
    this.selectedDoctor = doctor;
    this.doctorApiService.getAppointments(doctor.uuid).subscribe(data => {
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
