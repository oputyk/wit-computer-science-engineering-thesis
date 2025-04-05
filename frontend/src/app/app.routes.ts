import { Routes } from '@angular/router';
import { autoLoginPartialRoutesGuard } from 'angular-auth-oidc-client';
import { HomeComponent } from './home/home.component';
import { UnauthorizedComponent } from './unauthorized/unauthorized.component';
import { DoctorsComponent } from './doctors/doctors.component';
import { PatientsComponent } from './patients/patients.component';
import { DoctorAppointmentsComponent } from './doctor-appointments/doctor-appointments.component';
import { PatientAppointmentsComponent } from './patient-appointments/patient-appointments.component';
import { SpecialtiesComponent } from './specialties/specialties.component';
import { ServicesComponent } from './services/services.component';
import { AvailabilityComponent } from './availability/availability.component';

export const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    canActivate: [autoLoginPartialRoutesGuard],
  },
  {
    path: 'doctors',
    component: DoctorsComponent,
    canActivate: [autoLoginPartialRoutesGuard],
  },
  {
    path: 'patients',
    component: PatientsComponent,
    canActivate: [autoLoginPartialRoutesGuard],
  },
  {
    path: 'specialties',
    component: SpecialtiesComponent,
    canActivate: [autoLoginPartialRoutesGuard],
  },
  {
    path: 'services',
    component: ServicesComponent,
    canActivate: [autoLoginPartialRoutesGuard],
  },
  {
    path: 'doctor-appointments',
    component: DoctorAppointmentsComponent,
    canActivate: [autoLoginPartialRoutesGuard],
  },
  {
    path: 'availability',
    component: AvailabilityComponent,
    canActivate: [autoLoginPartialRoutesGuard],
  },
  {
    path: 'patient-appointments',
    component: PatientAppointmentsComponent,
    canActivate: [autoLoginPartialRoutesGuard],
  },
  { path: 'unauthorized', component: UnauthorizedComponent },
];
