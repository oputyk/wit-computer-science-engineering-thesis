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
import kamilceglinski.wit.greathealth.dto.SpecialtyResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Transactional
@Component
public class DoctorMapper {

    private final SpecialtyMapper specialtyMapper;

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
        dto.setSpecialties(savedDoctorEntity.getSpecialties().stream()
            .map(this::toSpecialtyResponseDTO)
            .collect(Collectors.toList()));
        return dto;
    }

    private SpecialtyResponseDTO toSpecialtyResponseDTO(DoctorSpecialtyEntity doctorSpecialtyEntity) {
        SpecialtyResponseDTO specialtyResponseDTO = new SpecialtyResponseDTO();
        specialtyResponseDTO.setUuid(doctorSpecialtyEntity.getSpecialty().getUuid());
        specialtyResponseDTO.setName(doctorSpecialtyEntity.getSpecialty().getName());
        return specialtyResponseDTO;
    }
}
