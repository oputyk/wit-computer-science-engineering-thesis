import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import { Observable } from 'rxjs';
import { Appointment } from '../models/appointment';
import { Patient } from '../models/patient';
import { PatientRequest } from '../models/patient-request';
import { AppointmentRequest } from '../models/appointment-request';
import { AvailableAppointmentTime } from '../models/available-appointment-time';
import moment from 'moment';

@Injectable({providedIn: 'root'})
export class PatientApiService {
    constructor(private http: HttpClient) { }

    createPatient(patientRequest: PatientRequest): Observable<Patient> {
        return this.http.post<Patient>('api/patients', patientRequest);
    }

    updatePatient(patientUuid: string, patientRequest: PatientRequest): Observable<Patient> {
        return this.http.put<Patient>('api/patients/' + patientUuid, patientRequest);
    }

    deletePatient(patientUuid: string): Observable<void> {
        return this.http.delete<void>('api/patients/' + patientUuid);
    }

    getPatientById(patientUuid: string): Observable<Patient> {
        return this.http.get<Patient>('api/patients/' + patientUuid);
    }
    
    getCurrentPatient(): Observable<Patient> {
        return this.http.get<Patient>('api/patients/current')
    }

    getAllPatients(): Observable<Patient[]> {
        return this.http.get<Patient[]>('api/patients');
    }

    createAppointment(patientUuid: string, appointmentRequest: AppointmentRequest): Observable<Appointment> {
        return this.http.post<Appointment>('api/patients/' + patientUuid + '/appointments', appointmentRequest);
    }

    getAvailableAppointmentTimes(patientUuid: string, doctorUuid: string, serviceUuid: string, date: Date): Observable<AvailableAppointmentTime[]> {
        date = moment(date).utcOffset(0, true).toDate()
        return this.http.get<AvailableAppointmentTime[]>('api/patients/' + patientUuid + '/available-appointment-times'
            + '?doctorUuid=' + doctorUuid + '&serviceUuid=' + serviceUuid + '&date=' + date.toISOString().split('T')[0]);
    }

    getAppointments(patientUuid: string): Observable<Appointment[]> {
        return this.http.get<Appointment[]>('api/patients/' + patientUuid + '/appointments');
    }

    cancelAppointment(patientUuid: string, appointmentUuid: string): Observable<void> {
        return this.http.delete<void>('api/patients/' + patientUuid + '/appointments/' + appointmentUuid);
    }
}