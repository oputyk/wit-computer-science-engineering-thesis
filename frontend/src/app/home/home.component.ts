import { AsyncPipe, JsonPipe } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { OidcSecurityService } from 'angular-auth-oidc-client';

@Component({
    selector: 'app-home',
    templateUrl: 'home.component.html',
    imports: [AsyncPipe, JsonPipe]
})
export class HomeComponent implements OnInit {
  private readonly oidcSecurityService = inject(OidcSecurityService);

  userData$ = this.oidcSecurityService.userData$;
  role: string;
  isAuthenticated = false;

  ngOnInit(): void {
    this.oidcSecurityService.isAuthenticated$.subscribe(
      ({ isAuthenticated }) => {
        this.isAuthenticated = isAuthenticated;

        console.warn('authenticated: ', isAuthenticated);
      }
    );
    this.userData$.subscribe(userData => {
      if (userData.userData.groups.includes('57057a39-a18f-4aed-bea8-c0f661ff12f0')) {
        this.role = "Adminie";
      }
      if (userData.userData.groups.includes('2350f29d-627c-4bd0-b554-e757825ba21c')) {
        this.role = "Doktorze";
      }
      if (userData.userData.groups.includes('973529ee-6146-4109-b7ef-21ea5f38efe2')) {
        this.role = "Pacjencie";
      }
    })
  }
}