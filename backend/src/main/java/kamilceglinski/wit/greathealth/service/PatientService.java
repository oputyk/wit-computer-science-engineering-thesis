package kamilceglinski.wit.greathealth.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import kamilceglinski.wit.greathealth.data.entity.AppointmentEntity;
import kamilceglinski.wit.greathealth.data.entity.DoctorEntity;
import kamilceglinski.wit.greathealth.data.entity.PatientEntity;
import kamilceglinski.wit.greathealth.data.entity.ServiceEntity;
import kamilceglinski.wit.greathealth.data.repository.AppointmentRepository;
import kamilceglinski.wit.greathealth.data.repository.DoctorRepository;
import kamilceglinski.wit.greathealth.data.repository.PatientRepository;
import kamilceglinski.wit.greathealth.data.repository.ServiceRepository;
import kamilceglinski.wit.greathealth.dto.AppointmentRequestDTO;
import kamilceglinski.wit.greathealth.dto.AppointmentResponseDTO;
import kamilceglinski.wit.greathealth.dto.PatientRequestDTO;
import kamilceglinski.wit.greathealth.dto.PatientResponseDTO;
import kamilceglinski.wit.greathealth.mapper.AppointmentMapper;
import kamilceglinski.wit.greathealth.mapper.PatientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Transactional
@RequiredArgsConstructor
@Service
public class PatientService {

    private final PatientMapper patientMapper;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final DoctorRepository doctorRepository;
    private final ServiceRepository serviceRepository;

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

    public AppointmentResponseDTO createAppointment(String uuid, AppointmentRequestDTO requestDTO) {
        PatientEntity patientEntity = ;
        DoctorEntity doctorEntity = ;
        ServiceEntity serviceEntity = serviceRepository.findById();
//        TODO: validate if selected service is allowed for selected doctor
        AppointmentEntity appointmentEntity = appointmentMapper.toAppointmentEntity(requestDTO, patientEntity, doctorEntity, serviceEntity);
        AppointmentEntity savedAppointmentEntity = appointmentRepository.save(appointmentEntity);
        return appointmentMapper.toAppointmentResponseDTO(savedAppointmentEntity);
    }

    public AppointmentResponseDTO getAppointments(String uuid) {
        return null;
    }

    public AppointmentResponseDTO cancelAppointment(String uuid, String appointmentUuid) {
        return null;
    }
}
