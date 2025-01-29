package kamilceglinski.wit.greathealth.mapper;

import jakarta.transaction.Transactional;
import kamilceglinski.wit.greathealth.data.entity.ServiceEntity;
import kamilceglinski.wit.greathealth.data.entity.SpecialtyEntity;
import kamilceglinski.wit.greathealth.dto.ServiceRequestDTO;
import kamilceglinski.wit.greathealth.dto.ServiceResponseDTO;
import kamilceglinski.wit.greathealth.dto.SpecialtyResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Transactional
@RequiredArgsConstructor
@Component
public class ServiceMapper {

    private final SpecialtyMapper specialtyMapper;

    public ServiceEntity toServiceEntity(ServiceRequestDTO requestDTO, SpecialtyEntity specialtyEntity) {
        ServiceEntity serviceEntity = new ServiceEntity();
        return updateServiceEntity(requestDTO, serviceEntity, specialtyEntity);
    }

    public ServiceEntity updateServiceEntity(ServiceRequestDTO requestDTO, ServiceEntity doctorEntity,
                                             SpecialtyEntity specialtyEntity) {
        doctorEntity.setName(requestDTO.getName());
        doctorEntity.setTimeInMinutes(requestDTO.getTimeInMinutes());
        doctorEntity.setSpecialty(specialtyEntity);
        return doctorEntity;
    }

    public ServiceResponseDTO toServiceResponseDTO(ServiceEntity savedServiceEntity) {
        ServiceResponseDTO dto = new ServiceResponseDTO();
        dto.setUuid(savedServiceEntity.getUuid());
        dto.setName(savedServiceEntity.getName());
        dto.setTimeInMinutes(savedServiceEntity.getTimeInMinutes());
        SpecialtyResponseDTO specialtyResponseDTO = specialtyMapper.toSpecialtyResponseDTO(savedServiceEntity.getSpecialty());
        dto.setSpecialty(specialtyResponseDTO);
        return dto;
    }
}
