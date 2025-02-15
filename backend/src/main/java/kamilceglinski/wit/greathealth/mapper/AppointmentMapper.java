package kamilceglinski.wit.greathealth.mapper;

import kamilceglinski.wit.greathealth.data.entity.AppointmentEntity;
import kamilceglinski.wit.greathealth.data.entity.DoctorEntity;
import kamilceglinski.wit.greathealth.data.entity.PatientEntity;
import kamilceglinski.wit.greathealth.data.entity.ServiceEntity;
import kamilceglinski.wit.greathealth.dto.AppointmentRequestDTO;
import kamilceglinski.wit.greathealth.dto.AppointmentResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AppointmentMapper {

    private final PatientMapper patientMapper;
    private final DoctorMapper doctorMapper;
    private final ServiceMapper serviceMapper;

    public AppointmentEntity toAppointmentEntity(AppointmentRequestDTO requestDTO, PatientEntity patientEntity,
                                             DoctorEntity doctorEntity, ServiceEntity serviceEntity) {
        AppointmentEntity appointmentEntity = new AppointmentEntity();
        return updateAppointmentEntity(requestDTO, patientEntity, doctorEntity, serviceEntity, appointmentEntity);
    }

    public AppointmentEntity updateAppointmentEntity(AppointmentRequestDTO requestDTO, PatientEntity patientEntity,
                                                 DoctorEntity doctorEntity, ServiceEntity serviceEntity,
                                                 AppointmentEntity appointmentEntity) {
        appointmentEntity.setPatient(patientEntity);
        appointmentEntity.setDoctor(doctorEntity);
        appointmentEntity.setService(serviceEntity);
        appointmentEntity.setDateTimeFrom(requestDTO.getDateTimeFrom());
        appointmentEntity.setStatus(requestDTO.getStatus());
        return appointmentEntity;
    }

    public AppointmentResponseDTO toAppointmentResponseDTO(AppointmentEntity savedAppointmentEntity) {
        AppointmentResponseDTO dto = new AppointmentResponseDTO();
        dto.setUuid(savedAppointmentEntity.getUuid());
        dto.setPatient(patientMapper.toPatientResponseDTO(savedAppointmentEntity.getPatient()));
        dto.setDoctor(doctorMapper.toDoctorResponseDTO(savedAppointmentEntity.getDoctor()));
        dto.setService(serviceMapper.toServiceResponseDTO(savedAppointmentEntity.getService()));
        dto.setDateTimeFrom(savedAppointmentEntity.getDateTimeFrom());
        dto.setStatus(savedAppointmentEntity.getStatus());
        return dto;
    }
}
