import { Doctor } from "./doctor";
import { Service } from "./service";

export interface AvailableAppointmentTime {
    doctor: Doctor;
    service: Service;
    dateTimeFrom: Date;
}