import { Component, inject, } from '@angular/core';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import { CommonModule } from '@angular/common';
import {MatCardModule} from '@angular/material/card';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import { UserApiService } from '../api/services/user-api.service';
import { DoctorAvailabilityApiService } from '../api/services/doctor-availability-api.service';
import { Availability } from '../api/models/availability';
import { MatDialog } from '@angular/material/dialog';
import { DoctorsAvailabilityRequest } from '../api/models/doctors-availability-request';
import { AvailabilityFormComponent } from '../availability-form/availability-form.component';

@Component({
  selector: 'app-availability',
  imports: [MatTableModule, CommonModule, MatCardModule, MatIconModule, MatButtonModule],
  templateUrl: './availability.component.html',
  styleUrl: './availability.component.css'
})
export class AvailabilityComponent {
  doctorAvailabilityApiService = inject(DoctorAvailabilityApiService)
  userApiService = inject(UserApiService)
  displayedColumnsAvailability: string[] = ['dateTimeFrom','dateTimeTill'];
  dataSourceAvailabilities: MatTableDataSource<Availability>;
  loggedInDoctorUuid: string;
  readonly dialog = inject(MatDialog);

  ngOnInit(): void {
    this.userApiService.getCurrentUserUuid().subscribe(doctorUuid => {
      this.loggedInDoctorUuid = doctorUuid.uuid;
      this.refreshTable();
    })
  }

  create(): void {
    const dialogRef = this.dialog.open(AvailabilityFormComponent, {
      data: {},
      height: '400px',
      width: '600px',
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result !== undefined) {
        this.doctorAvailabilityApiService.createAvailability(this.loggedInDoctorUuid, result as DoctorsAvailabilityRequest).subscribe(res => {
          this.refreshTable();
        })
      }
    });
  }

  refreshTable(): void {
    this.doctorAvailabilityApiService.getAllAvailabilities(this.loggedInDoctorUuid).subscribe(availabilities => {
      this.dataSourceAvailabilities = new MatTableDataSource<Availability>(availabilities);
    })
  }
}
