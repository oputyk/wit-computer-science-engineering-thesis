package kamilceglinski.wit.greathealth.mapper;

import kamilceglinski.wit.greathealth.data.entity.PatientEntity;
import kamilceglinski.wit.greathealth.dto.PatientRequestDTO;
import kamilceglinski.wit.greathealth.dto.PatientResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper {

    public PatientEntity toPatientEntity(PatientRequestDTO requestDTO) {
        PatientEntity doctorEntity = new PatientEntity();
        return updatePatientEntity(requestDTO, doctorEntity);
    }

    public PatientEntity updatePatientEntity(PatientRequestDTO requestDTO, PatientEntity doctorEntity) {
        doctorEntity.setEmail(requestDTO.getEmail());
        doctorEntity.setName(requestDTO.getName());
        doctorEntity.setSurname(requestDTO.getSurname());
        doctorEntity.setPesel(requestDTO.getPesel());
        return doctorEntity;
    }

    public PatientResponseDTO toPatientResponseDTO(PatientEntity savedPatientEntity) {
        PatientResponseDTO dto = new PatientResponseDTO();
        dto.setUuid(savedPatientEntity.getUuid());
        dto.setEmail(savedPatientEntity.getEmail());
        dto.setName(savedPatientEntity.getName());
        dto.setSurname(savedPatientEntity.getSurname());
        dto.setPesel(savedPatientEntity.getPesel());
        return dto;
    }
}
