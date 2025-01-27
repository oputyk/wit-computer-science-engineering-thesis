package kamilceglinski.wit.greathealth.mapper;

import jakarta.transaction.Transactional;
import kamilceglinski.wit.greathealth.data.entity.ServiceEntity;
import kamilceglinski.wit.greathealth.dto.ServiceRequestDTO;
import kamilceglinski.wit.greathealth.dto.ServiceResponseDTO;
import org.springframework.stereotype.Component;

@Transactional
@Component
public class ServiceMapper {

    public ServiceEntity toServiceEntity(ServiceRequestDTO requestDTO) {
        ServiceEntity doctorEntity = new ServiceEntity();
        return updateServiceEntity(requestDTO, doctorEntity);
    }

    public ServiceEntity updateServiceEntity(ServiceRequestDTO requestDTO, ServiceEntity doctorEntity) {
        doctorEntity.setName(requestDTO.getName());
        doctorEntity.setTimeInMinutes(requestDTO.getTimeInMinutes());
        return doctorEntity;
    }

    public ServiceResponseDTO toServiceResponseDTO(ServiceEntity savedServiceEntity) {
        ServiceResponseDTO dto = new ServiceResponseDTO();
        dto.setUuid(savedServiceEntity.getUuid());
        dto.setName(savedServiceEntity.getName());
        dto.setTimeInMinutes(savedServiceEntity.getTimeInMinutes());
        return dto;
    }
}
