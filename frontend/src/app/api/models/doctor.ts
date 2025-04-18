import { Specialty } from "./specialty";

export interface Doctor {
    uuid: string;
    email: string;
    name: string;
    surname: string;
    pesel: string;
    specialties: Specialty[];
}