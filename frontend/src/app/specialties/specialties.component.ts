import { Component, inject } from '@angular/core';
import { SpecialtyApiService } from '../api/services/specialty-api.service';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import {MatCardModule} from '@angular/material/card';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import { Specialty } from '../api/models/specialty';
import { MatDialog } from '@angular/material/dialog';
import { SpecialtyRequest } from '../api/models/specialty-request';
import { SpecialtyFormComponent } from '../specialty-form/specialty-form.component';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-specialties',
  imports: [MatTableModule, CommonModule, MatCardModule, MatIconModule, MatButtonModule],
  templateUrl: './specialties.component.html',
  styleUrl: './specialties.component.css'
})
export class SpecialtiesComponent {
  specialtyApiService = inject(SpecialtyApiService)
  displayedColumns: string[] = ['name','action'];
  dataSource: MatTableDataSource<Specialty>;
  readonly dialog = inject(MatDialog);

  ngOnInit(): void {
    this.refreshTable();
  }

  create(): void {
    const dialogRef = this.dialog.open(SpecialtyFormComponent, {
      data: {},
      height: '400px',
      width: '600px',
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result !== undefined) {
        this.specialtyApiService.createSpecialty(result as SpecialtyRequest).subscribe(res => {
          this.refreshTable();
        })
      }
    });
  }

  delete(specialty: Specialty): void {
    this.specialtyApiService.deleteSpecialty(specialty.uuid).subscribe(res => {
      this.refreshTable();
    })
  }

  refreshTable(): void {
    this.specialtyApiService.getAllSpecialties().subscribe(data => {
      this.dataSource = new MatTableDataSource<Specialty>(data);
    })
  }
}
