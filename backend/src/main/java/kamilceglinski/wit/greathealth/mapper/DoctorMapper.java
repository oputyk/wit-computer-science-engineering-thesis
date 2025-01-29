package kamilceglinski.wit.greathealth.mapper;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import kamilceglinski.wit.greathealth.data.entity.DoctorEntity;
import kamilceglinski.wit.greathealth.data.entity.DoctorSpecialtyEntity;
import kamilceglinski.wit.greathealth.data.entity.SpecialtyEntity;
import kamilceglinski.wit.greathealth.dto.DoctorRequestDTO;
import kamilceglinski.wit.greathealth.dto.DoctorResponseDTO;
import org.springframework.stereotype.Component;

@Transactional
@Component
public class DoctorMapper {

    public DoctorEntity toDoctorEntity(DoctorRequestDTO requestDTO) {
        DoctorEntity doctorEntity = new DoctorEntity();
        doctorEntity.setSpecialties(new ArrayList<>());
        return updateDoctorEntity(requestDTO, doctorEntity);
    }

    public DoctorEntity updateDoctorEntity(DoctorRequestDTO requestDTO, DoctorEntity doctorEntity) {
        doctorEntity.setEmail(requestDTO.getEmail());
        doctorEntity.setName(requestDTO.getName());
        doctorEntity.setSurname(requestDTO.getSurname());
        doctorEntity.setPesel(requestDTO.getPesel());
        return doctorEntity;
    }

    public DoctorResponseDTO toDoctorResponseDTO(DoctorEntity savedDoctorEntity) {
        DoctorResponseDTO dto = new DoctorResponseDTO();
        dto.setUuid(savedDoctorEntity.getUuid());
        dto.setEmail(savedDoctorEntity.getEmail());
        dto.setName(savedDoctorEntity.getName());
        dto.setSurname(savedDoctorEntity.getSurname());
        dto.setPesel(savedDoctorEntity.getPesel());
        List<String> specialties = savedDoctorEntity.getSpecialties().stream()
            .map(DoctorSpecialtyEntity::getSpecialty)
            .map(SpecialtyEntity::getName)
            .collect(Collectors.toList());
        dto.setSpecialties(specialties);
        return dto;
    }
}
