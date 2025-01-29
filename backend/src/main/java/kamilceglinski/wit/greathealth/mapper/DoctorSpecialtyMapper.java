package kamilceglinski.wit.greathealth.mapper;

import jakarta.transaction.Transactional;
import kamilceglinski.wit.greathealth.data.entity.DoctorSpecialtyEntity;
import kamilceglinski.wit.greathealth.data.entity.SpecialtyEntity;
import kamilceglinski.wit.greathealth.dto.DoctorSpecialtyResponseDTO;
import org.springframework.stereotype.Component;

@Transactional
@Component
public class DoctorSpecialtyMapper {

    public DoctorSpecialtyResponseDTO toDoctorResponseDTO(DoctorSpecialtyEntity savedDoctorSpecialtyEntity) {
        DoctorSpecialtyResponseDTO responseDTO = new DoctorSpecialtyResponseDTO();
        SpecialtyEntity specialty = savedDoctorSpecialtyEntity.getSpecialty();
        responseDTO.setUuid(specialty.getUuid());
        responseDTO.setName(specialty.getName());
        return responseDTO;
    }
}
