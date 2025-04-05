import { SpecialtyRequest } from './../models/specialty-request';
import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import { Observable } from 'rxjs';
import { Specialty } from '../models/specialty';

@Injectable({providedIn: 'root'})
export class SpecialtyApiService {
    constructor(private http: HttpClient) { }

    createSpecialty(specialtyRequest: SpecialtyRequest): Observable<Specialty> {
        return this.http.post<Specialty>('api/specialties', specialtyRequest);
    }

    getSpecialtyById(specialtyUuid: string): Observable<Specialty> {
        return this.http.get<Specialty>('api/specialties/' + specialtyUuid);
    }

    deleteSpecialty(specialtyUuid: string): Observable<void> {
        return this.http.delete<void>('api/specialties/' + specialtyUuid);
    }

    getAllSpecialties(): Observable<Specialty[]> {
        return this.http.get<Specialty[]>('api/specialties');
    }
}