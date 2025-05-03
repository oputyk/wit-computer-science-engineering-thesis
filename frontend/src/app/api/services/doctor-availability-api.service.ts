import { DoctorsAvailabilityRequest } from './../models/doctors-availability-request';
import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import { Observable } from 'rxjs';
import { Availability } from '../models/availability';
import moment from 'moment';

@Injectable({providedIn: 'root'})
export class DoctorAvailabilityApiService {
    constructor(private http: HttpClient) { }

    createAvailability(doctorUuid: string, doctorsAvailabilityRequest: DoctorsAvailabilityRequest): Observable<Availability> {
        doctorsAvailabilityRequest.dateTimeFrom = moment(doctorsAvailabilityRequest.dateTimeFrom).utcOffset(0, true).toDate()
        doctorsAvailabilityRequest.dateTimeTill = moment(doctorsAvailabilityRequest.dateTimeTill).utcOffset(0, true).toDate()
        return this.http.post<Availability>('api/doctors/' + doctorUuid + '/availabilities', doctorsAvailabilityRequest);
    }

    getAvailabilityById(doctorUuid: string, availabilityUuid: string): Observable<Availability> {
        return this.http.get<Availability>('api/doctors/' + doctorUuid + '/availabilities/' + availabilityUuid);  
    }

    getAllAvailabilities(doctorUuid: string): Observable<Availability[]> {
        return this.http.get<Availability[]>('api/doctors/' + doctorUuid + '/availabilities');  
    }
}