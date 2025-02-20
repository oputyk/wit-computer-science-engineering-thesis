package kamilceglinski.wit.greathealth.service;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import kamilceglinski.wit.greathealth.data.entity.AppointmentEntity;
import kamilceglinski.wit.greathealth.data.entity.AvailabilityEntity;
import kamilceglinski.wit.greathealth.data.entity.DoctorEntity;
import kamilceglinski.wit.greathealth.data.entity.DoctorSpecialtyEntity;
import kamilceglinski.wit.greathealth.data.entity.PatientEntity;
import kamilceglinski.wit.greathealth.data.entity.ServiceEntity;
import kamilceglinski.wit.greathealth.data.entity.SpecialtyEntity;
import kamilceglinski.wit.greathealth.data.entity.StatusEnum;
import kamilceglinski.wit.greathealth.data.repository.AppointmentRepository;
import kamilceglinski.wit.greathealth.data.repository.AvailabilityRepository;
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
    private final AvailabilityRepository availabilityRepository;

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

    public AppointmentResponseDTO createAppointment(String patientUuid, AppointmentRequestDTO requestDTO) {
        PatientEntity patientEntity = patientRepository.findById(patientUuid)
            .orElseThrow();
        DoctorEntity doctorEntity = doctorRepository.findById(requestDTO.getDoctorUuid())
            .orElseThrow();
        List<SpecialtyEntity> specialties = doctorEntity.getSpecialties().stream()
            .map(DoctorSpecialtyEntity::getSpecialty)
            .toList();
        ServiceEntity serviceEntity = serviceRepository.findBySpecialtyInAndUuid(specialties, requestDTO.getServiceUuid())
            .orElseThrow();
        LocalDateTime tillDateTime = requestDTO.getDateTimeFrom().plusMinutes(serviceEntity.getTimeInMinutes());
        AvailabilityEntity availabilityEntity = availabilityRepository.findByDoctorUuidAndDateTime(
                requestDTO.getDoctorUuid(), requestDTO.getDateTimeFrom(), tillDateTime)
            .orElseThrow();

        LocalDateTime availabilityDateTimeFrom = availabilityEntity.getDateTimeFrom();
        LocalDateTime availabilityDateTimeTill = availabilityEntity.getDateTimeTill();

        List<AppointmentEntity> existingAppointments = appointmentRepository.findByDateTimeFromGreaterThanOrEqualAndDateTimeFromLessThanOrEqualThan(
            availabilityDateTimeFrom, availabilityDateTimeTill);
        for (AppointmentEntity existingAppointment : existingAppointments) {
            // TODO: check if existing appointments do not collide with the newly created one
        }

        AppointmentEntity appointmentEntity = appointmentMapper.toAppointmentEntity(requestDTO, patientEntity, doctorEntity, serviceEntity);
        AppointmentEntity savedAppointmentEntity = appointmentRepository.save(appointmentEntity);
        return appointmentMapper.toAppointmentResponseDTO(savedAppointmentEntity);
    }

    public List<AppointmentResponseDTO> getAppointments(String patientUuid) {
        PatientEntity patientEntity = patientRepository.findById(patientUuid)
            .orElseThrow();
        return appointmentRepository.findByPatient(patientEntity).stream()
            .map(appointmentMapper::toAppointmentResponseDTO)
            .collect(Collectors.toList());
    }

    public AppointmentResponseDTO cancelAppointment(String patientUuid, String appointmentUuid) {
        PatientEntity patientEntity = patientRepository.findById(patientUuid)
            .orElseThrow();
        AppointmentEntity appointmentEntity = appointmentRepository.findByPatientAndUuid(patientEntity, appointmentUuid)
            .orElseThrow();
        appointmentEntity.setStatus(StatusEnum.CANCELED);
        AppointmentEntity savedAppointmentEntity = appointmentRepository.save(appointmentEntity);
        return appointmentMapper.toAppointmentResponseDTO(savedAppointmentEntity);
    }
}
