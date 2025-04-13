import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import { Observable } from 'rxjs';

@Injectable({providedIn: 'root'})
export class ServiceApiService {
    constructor(private http: HttpClient) { }

    getCurrentUserUuid(): Observable<string> {
        return this.http.get<string>('api/users/current/uuid');
    }
}