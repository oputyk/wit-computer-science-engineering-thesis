import { Doctor } from "./doctor";
import { Patient } from "./patient";
import { Service } from "./service";

export interface Appointment {
    uuid: string;
    patient: Patient;
    doctor: Doctor;
    service: Service;
    dateTimeFrom: Date;
    status: string;
}