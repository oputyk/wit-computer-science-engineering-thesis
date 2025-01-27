package kamilceglinski.wit.greathealth.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import kamilceglinski.wit.greathealth.data.entity.PatientEntity;
import kamilceglinski.wit.greathealth.data.repository.PatientRepository;
import kamilceglinski.wit.greathealth.dto.PatientRequestDTO;
import kamilceglinski.wit.greathealth.dto.PatientResponseDTO;
import kamilceglinski.wit.greathealth.mapper.PatientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Transactional
@RequiredArgsConstructor
@Service
public class PatientService {

    private final PatientMapper patientMapper;
    private final PatientRepository patientRepository;

    public PatientResponseDTO createPatient(PatientRequestDTO requestDTO) {
        PatientEntity patientEntity = patientMapper.toPatientEntity(requestDTO);
        PatientEntity savedPatientEntity = patientRepository.save(patientEntity);
        return patientMapper.toPatientResponseDTO(savedPatientEntity);
    }

    public PatientResponseDTO getPatientByUuId(String uuid) {
        return patientRepository.findById(uuid)
            .map(patientMapper::toPatientResponseDTO)
            .orElseThrow();
    }

    public List<PatientResponseDTO> getAllPatients() {
        return patientRepository.findAll().stream()
            .map(patientMapper::toPatientResponseDTO)
            .collect(Collectors.toList());
    }

    public PatientResponseDTO updatePatient(PatientRequestDTO requestDTO, String uuid) {
        return patientRepository.findById(uuid)
            .map(patientEntity -> patientMapper.updatePatientEntity(requestDTO, patientEntity))
            .map(patientRepository::save)
            .map(patientMapper::toPatientResponseDTO)
            .orElseThrow();
    }
}
