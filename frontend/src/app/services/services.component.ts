import { Component, inject } from '@angular/core';
import { ServiceApiService } from '../api/services/service-api.service';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import {MatCardModule} from '@angular/material/card';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import { Service } from '../api/models/service';
import { MatDialog } from '@angular/material/dialog';
import { ServiceRequest } from '../api/models/service-request';
import { ServiceFormComponent } from '../service-form/service-form.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-services',
  imports: [MatTableModule, CommonModule, MatCardModule, MatIconModule, MatButtonModule],
  templateUrl: './services.component.html',
  styleUrl: './services.component.css'
})
export class ServicesComponent {
  serviceApiService = inject(ServiceApiService)
  displayedColumns: string[] = ['name', 'timeInMinutes', 'specialty', 'action'];
  dataSource: MatTableDataSource<Service>;
  readonly dialog = inject(MatDialog);

  ngOnInit(): void {
    this.refreshTable();
  }

  create(): void {
    const dialogRef = this.dialog.open(ServiceFormComponent, {
      data: {},
      height: '400px',
      width: '600px',
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result !== undefined) {
        const service = result as Service
        this.serviceApiService.createService({
          name: service.name,
          timeInMinutes: service.timeInMinutes,
          specialtyUuid: service.specialty.uuid
        } as ServiceRequest).subscribe(res => {
          this.refreshTable();
        })
      }
    });
  }

  delete(service: Service): void {
    this.serviceApiService.deleteService(service.uuid).subscribe(res => {
      this.refreshTable();
    })
  }

  refreshTable(): void {
    this.serviceApiService.getAllServices().subscribe(data => {
      this.dataSource = new MatTableDataSource<Service>(data);
    })
  }
}
