package kamilceglinski.wit.greathealth.mapper;

import jakarta.transaction.Transactional;
import kamilceglinski.wit.greathealth.data.entity.DoctorEntity;
import kamilceglinski.wit.greathealth.data.entity.SpecialtyEntity;
import kamilceglinski.wit.greathealth.dto.SpecialtyNameResponseDTO;
import kamilceglinski.wit.greathealth.dto.SpecialtyRequestDTO;
import kamilceglinski.wit.greathealth.dto.SpecialtyResponseDTO;
import org.springframework.stereotype.Component;

@Transactional
@Component
public class SpecialtyMapper {

    public SpecialtyEntity toSpecialtyEntity(SpecialtyRequestDTO requestDTO, DoctorEntity doctorEntity) {
        SpecialtyEntity specialtyEntity = new SpecialtyEntity();
        return updateSpecialtyEntity(requestDTO, specialtyEntity, doctorEntity);
    }

    public SpecialtyEntity updateSpecialtyEntity(SpecialtyRequestDTO requestDTO, SpecialtyEntity specialtyEntity,
                                                 DoctorEntity doctorEntity) {
        specialtyEntity.setName(requestDTO.getName());
        specialtyEntity.setDoctor(doctorEntity);
        return specialtyEntity;
    }

    public SpecialtyResponseDTO toSpecialtyResponseDTO(SpecialtyEntity savedSpecialtyEntity) {
        SpecialtyResponseDTO dto = new SpecialtyResponseDTO();
        dto.setUuid(savedSpecialtyEntity.getUuid());
        dto.setName(savedSpecialtyEntity.getName());
        return dto;
    }

    public SpecialtyNameResponseDTO toSpecialtyNameResponseDTO(String name) {
        SpecialtyNameResponseDTO dto = new SpecialtyNameResponseDTO();
        dto.setName(name);
        return dto;
    }
}