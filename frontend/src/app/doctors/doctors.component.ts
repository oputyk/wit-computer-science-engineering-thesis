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

@Component({
  selector: 'app-doctors',
  imports: [MatTableModule, CommonModule, MatCardModule, MatIconModule, MatButtonModule],
  templateUrl: './doctors.component.html',
  styleUrl: './doctors.component.css'
})
export class DoctorsComponent {
  doctorApiService = inject(DoctorApiService)
  displayedColumns: string[] = ['email','name','surname','pesel','specialties', 'action'];
  dataSource: MatTableDataSource<Doctor>;
  readonly dialog = inject(MatDialog);

  ngOnInit(): void {
    this.refreshTable();
  }

  create(): void {
    const dialogRef = this.dialog.open(DoctorFormComponent, {
      data: {},
      height: '400px',
      width: '600px',
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result !== undefined) {
        this.doctorApiService.createDoctor(result as DoctorRequest).subscribe(res => {
          this.refreshTable();
        })
      }
    });
  }

  edit(doctor: Doctor): void {
    const dialogRef = this.dialog.open(DoctorFormComponent, {
      data: doctor,
      height: '400px',
      width: '600px',
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result !== undefined) {
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
}
