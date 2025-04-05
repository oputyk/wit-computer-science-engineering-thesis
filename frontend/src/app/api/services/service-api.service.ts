import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import { Observable } from 'rxjs';
import { ServiceRequest } from '../models/service-request';
import { Service } from '../models/service';

@Injectable({providedIn: 'root'})
export class ServiceApiService {
    constructor(private http: HttpClient) { }

    createService(serviceRequest: ServiceRequest): Observable<Service> {
        return this.http.post<Service>('api/services', serviceRequest);
    }

    getServiceById(serviceUuid: string): Observable<Service> {
        return this.http.get<Service>('api/services/' + serviceUuid);
    }

    deleteService(serviceUuid: string): Observable<void> {
        return this.http.delete<void>('api/services/' + serviceUuid);
    }

    getAllServices(): Observable<Service[]> {
        return this.http.get<Service[]>('api/services');
    }
}