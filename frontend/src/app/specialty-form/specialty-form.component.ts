import { Component, inject } from '@angular/core';
import { Specialty } from '../api/models/specialty';
import {
  MAT_DIALOG_DATA,
  MatDialog,
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle,
} from '@angular/material/dialog';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';

@Component({
  selector: 'app-specialty-form',
  imports: [
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    MatButtonModule,
    MatDialogTitle,
    MatDialogContent,
    MatDialogActions,
    MatDialogClose,
    MatCardModule
  ],
  templateUrl: './specialty-form.component.html',
  styleUrl: './specialty-form.component.css'
})
export class SpecialtyFormComponent {
  readonly dialogRef = inject(MatDialogRef<SpecialtyFormComponent>);
  readonly specialty = structuredClone(inject<Specialty>(MAT_DIALOG_DATA));

  cancel(): void {
    this.dialogRef.close();
  }

  save(): void {
    this.dialogRef.close(this.specialty);
  }
}
