import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import { Observable } from 'rxjs';
import { CurrentUserUuid } from '../models/current-user-uuid';

@Injectable({providedIn: 'root'})
export class UserApiService {
    constructor(private http: HttpClient) { }

    getCurrentUserUuid(): Observable<CurrentUserUuid> {
        return this.http.get<CurrentUserUuid>('api/users/current/uuid');
    }
}