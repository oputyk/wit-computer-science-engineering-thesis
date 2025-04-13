import { Injectable } from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import { Observable, switchMap } from 'rxjs';
import { OidcSecurityService } from 'angular-auth-oidc-client';

@Injectable({providedIn: 'root'})
export class TokenInterceptorService implements HttpInterceptor {

    constructor(private oidcSecurityService: OidcSecurityService) {}

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        console.log("hello")
        return this.oidcSecurityService.getIdToken().pipe(
            switchMap(token => {
                if (token) {
                req = req.clone({
                    setHeaders: {
                    Authorization: `Bearer ${token}`
                    }
                });
                }
                return next.handle(req);
            })
        );
    }
}