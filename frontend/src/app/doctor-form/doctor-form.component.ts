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
import {MatInputModule} from '@angular/material/input';
import { Doctor } from '../api/models/doctor';
import { Specialty } from '../api/models/specialty';
import { SpecialtyApiService } from '../api/services/specialty-api.service';

@Component({
  selector: 'app-doctor-form',
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
  ],
  templateUrl: './doctor-form.component.html',
  styleUrl: './doctor-form.component.css'
})
export class DoctorFormComponent {
  readonly dialogRef = inject(MatDialogRef<DoctorFormComponent>);
  readonly doctor = structuredClone(inject<Doctor>(MAT_DIALOG_DATA));
  specialties: Specialty[];  
  specialtyApiService = inject(SpecialtyApiService)
  specialtiesUuids: string[];

  ngOnInit(): void {
    this.specialtyApiService.getAllSpecialties().subscribe(data => {
      this.specialties = data;
    })
    this.specialtiesUuids = this.doctor.specialties.map(s => s.uuid)
  }

  cancel(): void {
    this.dialogRef.close();
  }

  save(): void {
    this.doctor.specialties = this.specialties.filter(s => this.specialtiesUuids.includes(s.uuid))
    this.dialogRef.close(this.doctor);
  }
}
