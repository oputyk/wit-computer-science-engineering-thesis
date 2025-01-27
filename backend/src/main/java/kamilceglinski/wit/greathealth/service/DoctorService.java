package kamilceglinski.wit.greathealth.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import kamilceglinski.wit.greathealth.data.entity.DoctorEntity;
import kamilceglinski.wit.greathealth.data.repository.DoctorRepository;
import kamilceglinski.wit.greathealth.dto.DoctorRequestDTO;
import kamilceglinski.wit.greathealth.dto.DoctorResponseDTO;
import kamilceglinski.wit.greathealth.mapper.DoctorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Transactional
@RequiredArgsConstructor
@Service
public class DoctorService {

    private final DoctorMapper doctorMapper;
    private final DoctorRepository doctorRepository;

    public DoctorResponseDTO createDoctor(DoctorRequestDTO requestDTO) {
        DoctorEntity doctorEntity = doctorMapper.toDoctorEntity(requestDTO);
        DoctorEntity savedDoctorEntity = doctorRepository.save(doctorEntity);
        return doctorMapper.toDoctorResponseDTO(savedDoctorEntity);
    }

    public DoctorResponseDTO getDoctorByUuId(String uuid) {
        return doctorRepository.findById(uuid)
            .map(doctorMapper::toDoctorResponseDTO)
            .orElseThrow();
    }

    public List<DoctorResponseDTO> getAllDoctors() {
        return doctorRepository.findAll().stream()
            .map(doctorMapper::toDoctorResponseDTO)
            .collect(Collectors.toList());
    }

    public DoctorResponseDTO updateDoctor(DoctorRequestDTO requestDTO, String uuid) {
        return doctorRepository.findById(uuid)
            .map(doctorEntity -> doctorMapper.updateDoctorEntity(requestDTO, doctorEntity))
            .map(doctorRepository::save)
            .map(doctorMapper::toDoctorResponseDTO)
            .orElseThrow();
    }
}
