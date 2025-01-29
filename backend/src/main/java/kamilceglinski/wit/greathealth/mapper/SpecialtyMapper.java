package kamilceglinski.wit.greathealth.mapper;

import jakarta.transaction.Transactional;
import kamilceglinski.wit.greathealth.data.entity.SpecialtyEntity;
import kamilceglinski.wit.greathealth.dto.SpecialtyRequestDTO;
import kamilceglinski.wit.greathealth.dto.SpecialtyResponseDTO;
import org.springframework.stereotype.Component;

@Transactional
@Component
public class SpecialtyMapper {

    public SpecialtyEntity toSpecialtyEntity(SpecialtyRequestDTO requestDTO) {
        SpecialtyEntity specialtyEntity = new SpecialtyEntity();
        return updateSpecialtyEntity(requestDTO, specialtyEntity);
    }

    public SpecialtyEntity updateSpecialtyEntity(SpecialtyRequestDTO requestDTO, SpecialtyEntity specialtyEntity) {
        specialtyEntity.setName(requestDTO.getName());
        return specialtyEntity;
    }

    public SpecialtyResponseDTO toSpecialtyResponseDTO(SpecialtyEntity savedSpecialtyEntity) {
        SpecialtyResponseDTO dto = new SpecialtyResponseDTO();
        dto.setUuid(savedSpecialtyEntity.getUuid());
        dto.setName(savedSpecialtyEntity.getName());
        return dto;
    }
}
