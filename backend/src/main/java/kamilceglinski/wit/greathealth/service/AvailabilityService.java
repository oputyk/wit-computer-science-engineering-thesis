package kamilceglinski.wit.greathealth.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import kamilceglinski.wit.greathealth.data.entity.AvailabilityEntity;
import kamilceglinski.wit.greathealth.data.entity.DoctorEntity;
import kamilceglinski.wit.greathealth.data.repository.AvailabilityRepository;
import kamilceglinski.wit.greathealth.data.repository.DoctorRepository;
import kamilceglinski.wit.greathealth.dto.AvailabilityRequestDTO;
import kamilceglinski.wit.greathealth.dto.AvailabilityResponseDTO;
import kamilceglinski.wit.greathealth.mapper.AvailabilityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Transactional
@RequiredArgsConstructor
@Service
public class AvailabilityService {

    private final AvailabilityMapper availabilityMapper;
    private final AvailabilityRepository availabilityRepository;
    private final DoctorRepository doctorRepository;

    public AvailabilityResponseDTO createAvailability(String doctorUuid, AvailabilityRequestDTO requestDTO) {
        DoctorEntity doctor = doctorRepository.findById(doctorUuid).orElseThrow();
        AvailabilityEntity availabilityEntity = availabilityMapper.toAvailabilityEntity(doctor, requestDTO);
        AvailabilityEntity savedAvailabilityEntity = availabilityRepository.save(availabilityEntity);
        return availabilityMapper.toAvailabilityResponseDTO(savedAvailabilityEntity);
    }

    public AvailabilityResponseDTO getAvailabilityByUuId(String doctorUuid, String uuid) {
        return availabilityRepository.findByDoctor_uuidAndUuid(doctorUuid, uuid)
            .map(availabilityMapper::toAvailabilityResponseDTO)
            .orElseThrow();
    }

    public List<AvailabilityResponseDTO> getAllAvailabilities(String doctorUuid) {
        return availabilityRepository.findAllByDoctor_uuid(doctorUuid).stream()
            .map(availabilityMapper::toAvailabilityResponseDTO)
            .collect(Collectors.toList());
    }
}
