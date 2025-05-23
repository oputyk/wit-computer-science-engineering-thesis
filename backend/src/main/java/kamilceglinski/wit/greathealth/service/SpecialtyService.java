package kamilceglinski.wit.greathealth.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import kamilceglinski.wit.greathealth.data.entity.SpecialtyEntity;
import kamilceglinski.wit.greathealth.data.repository.DoctorRepository;
import kamilceglinski.wit.greathealth.data.repository.SpecialtyRepository;
import kamilceglinski.wit.greathealth.dto.SpecialtyRequestDTO;
import kamilceglinski.wit.greathealth.dto.SpecialtyResponseDTO;
import kamilceglinski.wit.greathealth.mapper.SpecialtyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Transactional
@RequiredArgsConstructor
@Service
public class SpecialtyService {

    private final SpecialtyMapper specialtyMapper;
    private final SpecialtyRepository specialtyRepository;

    public SpecialtyResponseDTO createSpecialty(SpecialtyRequestDTO requestDTO) {
        SpecialtyEntity specialtyEntity = specialtyMapper.toSpecialtyEntity(requestDTO);
        SpecialtyEntity savedSpecialtyEntity = specialtyRepository.save(specialtyEntity);
        return specialtyMapper.toSpecialtyResponseDTO(savedSpecialtyEntity);
    }

    public SpecialtyResponseDTO getSpecialtyByUuId(String uuid) {
        return specialtyRepository.findById(uuid)
            .map(specialtyMapper::toSpecialtyResponseDTO)
            .orElseThrow();
    }

    public void deleteSpecialty(String uuid) {
        specialtyRepository.deleteByUuid(uuid);
    }

    public List<SpecialtyResponseDTO> getAllSpecialties() {
        return specialtyRepository.findAll().stream()
            .map(specialtyMapper::toSpecialtyResponseDTO)
            .collect(Collectors.toList());
    }
}
