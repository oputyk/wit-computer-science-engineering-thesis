import { Specialty } from "./specialty";

export interface Service {
    uuid: string;
    name: string;
    timeInMinutes: number;
    specialty: Specialty;
}