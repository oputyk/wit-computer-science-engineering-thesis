import { Component, inject } from '@angular/core';
import { PatientApiService } from '../api/services/patient-api.service';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import {MatCardModule} from '@angular/material/card';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import { Patient } from '../api/models/patient';
import { MatDialog } from '@angular/material/dialog';
import { PatientRequest } from '../api/models/patient-request';
import { PatientFormComponent } from '../patient-form/patient-form.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-patients',
  imports: [MatTableModule, CommonModule, MatCardModule, MatIconModule, MatButtonModule],
  templateUrl: './patients.component.html',
  styleUrl: './patients.component.css'
})
export class PatientsComponent {
  patientApiService = inject(PatientApiService)
  displayedColumns: string[] = ['email','name','surname','pesel', 'action'];
  dataSource: MatTableDataSource<Patient>;
  readonly dialog = inject(MatDialog);

  ngOnInit(): void {
    this.refreshTable();
  }

  create(): void {
    const dialogRef = this.dialog.open(PatientFormComponent, {
      data: {},
      height: '400px',
      width: '600px',
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result !== undefined) {
        this.patientApiService.createPatient(result as PatientRequest).subscribe(res => {
          this.refreshTable();
        })
      }
    });
  }

  edit(patient: Patient): void {
    const dialogRef = this.dialog.open(PatientFormComponent, {
      data: patient,
      height: '400px',
      width: '600px',
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result !== undefined) {
        this.patientApiService.updatePatient(patient.uuid, result as PatientRequest).subscribe(res => {
          this.refreshTable();
        })
      }
    });
  }

  delete(patient: Patient): void {
    this.patientApiService.deletePatient(patient.uuid).subscribe(res => {
      this.refreshTable();
    })
  }

  refreshTable(): void {
    this.patientApiService.getAllPatients().subscribe(data => {
      this.dataSource = new MatTableDataSource<Patient>(data);
    })
  }
}
