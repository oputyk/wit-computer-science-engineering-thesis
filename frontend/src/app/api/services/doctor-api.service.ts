import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Doctor} from "../models/doctor";
import { Observable } from 'rxjs';
import { DoctorRequest } from '../models/doctor-request';
import { DoctorSpecialty } from '../models/doctor-specialty';
import { DoctorSpecialtyRequest } from '../models/doctor-specialty-request';
import { Appointment } from '../models/appointment';

@Injectable({providedIn: 'root'})
export class DoctorApiService {
    constructor(private http: HttpClient) { }

    createDoctor(doctorRequest: DoctorRequest): Observable<Doctor> {
        return this.http.post<Doctor>('api/doctors', doctorRequest);
    }

    createDoctorSpecialty(doctorUuid: string, doctorSpecialtyRequest: DoctorSpecialtyRequest): Observable<DoctorSpecialty> {
        return this.http.post<DoctorSpecialty>('api/doctors/' + doctorUuid + '/specialties', doctorSpecialtyRequest);
    }

    deleteDoctorSpecialty(doctorUuid: string, specialtyUuid: string): Observable<void> {
        return this.http.delete<void>('api/doctors/' + doctorUuid + '/specialties/' + specialtyUuid);
    }

    updateDoctor(doctorUuid: string, doctorRequest: DoctorRequest): Observable<Doctor> {
        return this.http.put<Doctor>('api/doctors/' + doctorUuid, doctorRequest);
    }

    deleteDoctor(doctorUuid: string): Observable<void> {
        return this.http.delete<void>('api/doctors/' + doctorUuid);
    }

    getDoctorById(doctorUuid: string): Observable<Doctor> {
        return this.http.get<Doctor>('api/doctors/' + doctorUuid);
    }

    getAllDoctors(): Observable<Doctor[]> {
        return this.http.get<Doctor[]>('api/doctors');
    }

    getAppointments(doctorUuid: string): Observable<Appointment[]> {
        return this.http.get<Appointment[]>('api/doctors/' + doctorUuid + '/appointments');
    }

    finishAppointment(doctorUuid: string, appointmentUuid: string): Observable<Appointment> {
        return this.http.delete<Appointment>('api/doctors/' + doctorUuid + '/appointments/' + appointmentUuid);
    }
}