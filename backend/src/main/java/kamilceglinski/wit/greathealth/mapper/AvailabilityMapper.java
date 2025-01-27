package kamilceglinski.wit.greathealth.mapper;

import jakarta.transaction.Transactional;
import kamilceglinski.wit.greathealth.data.entity.AvailabilityEntity;
import kamilceglinski.wit.greathealth.data.entity.DoctorEntity;
import kamilceglinski.wit.greathealth.dto.AvailabilityRequestDTO;
import kamilceglinski.wit.greathealth.dto.AvailabilityResponseDTO;
import org.springframework.stereotype.Component;

@Transactional
@Component
public class AvailabilityMapper {

    public AvailabilityEntity toAvailabilityEntity(DoctorEntity doctor, AvailabilityRequestDTO requestDTO) {
        AvailabilityEntity availabilityEntity = new AvailabilityEntity();
        return updateAvailabilityEntity(doctor, requestDTO, availabilityEntity);
    }

    public AvailabilityEntity updateAvailabilityEntity(DoctorEntity doctor, AvailabilityRequestDTO requestDTO,
                                                       AvailabilityEntity availabilityEntity) {
        availabilityEntity.setDoctor(doctor);
        availabilityEntity.setDateTimeFrom(requestDTO.getDateTimeFrom());
        availabilityEntity.setDateTimeTill(requestDTO.getDateTimeTill());
        return availabilityEntity;
    }

    public AvailabilityResponseDTO toAvailabilityResponseDTO(AvailabilityEntity savedAvailabilityEntity) {
        AvailabilityResponseDTO dto = new AvailabilityResponseDTO();
        dto.setUuid(savedAvailabilityEntity.getUuid());
        dto.setDateTimeFrom(savedAvailabilityEntity.getDateTimeFrom());
        dto.setDateTimeTill(savedAvailabilityEntity.getDateTimeTill());
        return dto;
    }
}
