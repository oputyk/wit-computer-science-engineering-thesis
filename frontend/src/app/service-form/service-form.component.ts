import { Component, inject } from '@angular/core';
import { Patient } from '../api/models/patient';
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
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { Service } from '../api/models/service';
import { Specialty } from '../api/models/specialty';
import { SpecialtyApiService } from '../api/services/specialty-api.service';

@Component({
  selector: 'app-service-form',
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
  templateUrl: './service-form.component.html',
  styleUrl: './service-form.component.css'
})
export class ServiceFormComponent {
  specialties: Specialty[];  
  specialtyApiService = inject(SpecialtyApiService)
  readonly dialogRef = inject(MatDialogRef<ServiceFormComponent>);
  readonly service = structuredClone(inject<Service>(MAT_DIALOG_DATA));

  ngOnInit(): void {
    this.specialtyApiService.getAllSpecialties().subscribe(data => {
      this.specialties = data;
    })
  }

  cancel(): void {
    this.dialogRef.close();
  }

  save(): void {
    this.dialogRef.close(this.service);
  }

}
