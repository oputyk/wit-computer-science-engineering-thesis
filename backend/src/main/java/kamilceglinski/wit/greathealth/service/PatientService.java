package kamilceglinski.wit.greathealth.service;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
import kamilceglinski.wit.greathealth.dto.AvailableAppointmentTimeResponseDTO;
import kamilceglinski.wit.greathealth.dto.PatientRequestDTO;
import kamilceglinski.wit.greathealth.dto.PatientResponseDTO;
import kamilceglinski.wit.greathealth.mapper.AppointmentMapper;
import kamilceglinski.wit.greathealth.mapper.DoctorMapper;
import kamilceglinski.wit.greathealth.mapper.PatientMapper;
import kamilceglinski.wit.greathealth.mapper.ServiceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Transactional
@RequiredArgsConstructor
@Service
public class PatientService {

    private final PatientMapper patientMapper;
    private final PatientRepository patientRepository;
    private final DoctorMapper doctorMapper;
    private final ServiceMapper serviceMapper;
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

    public void deletePatient(String uuid) {
        patientRepository.deleteByUuid(uuid);
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
        if (!isDateTimeAvailable(doctorEntity, serviceEntity.getTimeInMinutes(),
            requestDTO.getDateTimeFrom())) {
            throw new RuntimeException("That date time is unavailable");
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

    public List<AvailableAppointmentTimeResponseDTO> getAvailableAppointmentTimes(String uuid, String doctorUuid,
                                                                                  String serviceUuid, LocalDate date) {
        PatientEntity patientEntity = patientRepository.findById(uuid)
            .orElseThrow();
        DoctorEntity doctorEntity = doctorRepository.findById(doctorUuid)
            .orElseThrow();
        List<SpecialtyEntity> specialties = doctorEntity.getSpecialties().stream()
            .map(DoctorSpecialtyEntity::getSpecialty)
            .toList();
        ServiceEntity serviceEntity = serviceRepository.findBySpecialtyInAndUuid(specialties, serviceUuid)
            .orElseThrow();
        int timeInMinutes = serviceEntity.getTimeInMinutes();

        LocalDateTime minLocalDateTime = date.atStartOfDay();
        LocalDateTime maxLocalDateTime = date.plusDays(1).atStartOfDay();
        LocalDateTime currentDateTime = minLocalDateTime;
        List<LocalDateTime> allDateTimesForGivenDay = new ArrayList<>();
        while (currentDateTime.plusMinutes(timeInMinutes).isBefore(maxLocalDateTime)) {
            if (isDateTimeAvailable(doctorEntity, timeInMinutes, currentDateTime)) {
                allDateTimesForGivenDay.add(currentDateTime);
            }
            currentDateTime = currentDateTime.plusMinutes(timeInMinutes);
        }

        return allDateTimesForGivenDay.stream()
            .map(localDateTime -> AvailableAppointmentTimeResponseDTO.builder()
                    .doctor(doctorMapper.toDoctorResponseDTO(doctorEntity))
                    .service(serviceMapper.toServiceResponseDTO(serviceEntity))
                    .dateTimeFrom(localDateTime)
                    .build()
            )
            .collect(Collectors.toList());
    }

    public boolean isDateTimeAvailable(DoctorEntity doctorEntity, int timeInMinutes, LocalDateTime newAppointmentDateTimeFrom) {
        LocalDateTime newAppointmentDateTimeTill = newAppointmentDateTimeFrom.plusMinutes(timeInMinutes);
        AvailabilityEntity availabilityEntity = availabilityRepository.findByDoctorUuidAndDateTime(
                doctorEntity.getUuid(), newAppointmentDateTimeFrom, newAppointmentDateTimeTill)
            .stream()
            .findFirst()
            .orElse(null);
        if (availabilityEntity == null) {
            return false;
        }

        LocalDateTime availabilityDateTimeFrom = availabilityEntity.getDateTimeFrom();
        LocalDateTime availabilityDateTimeTill = availabilityEntity.getDateTimeTill();

        List<AppointmentEntity> existingAppointments = appointmentRepository.findByAvailabilityTime(
            availabilityDateTimeFrom, availabilityDateTimeTill);
        for (AppointmentEntity existingAppointment : existingAppointments) {
            LocalDateTime existingAppointmentDateTimeFrom = existingAppointment.getDateTimeFrom();
            Integer existingAppointmentTimeInMinutes = existingAppointment.getService().getTimeInMinutes();
            LocalDateTime existingAppointmentDateTimeTill = existingAppointmentDateTimeFrom.plusMinutes(existingAppointmentTimeInMinutes);
            if (isInCollision(newAppointmentDateTimeFrom, newAppointmentDateTimeTill,
                existingAppointmentDateTimeFrom, existingAppointmentDateTimeTill)) {
                return false;
            }
        }
        return true;
    }

    static boolean isInCollision(LocalDateTime newAppointmentDateTimeFrom, LocalDateTime newAppointmentDateTimeTill,
                                 LocalDateTime existingAppointmentDateTimeFrom, LocalDateTime existingAppointmentDateTimeTill) {
        return isDatePointInDateRange(newAppointmentDateTimeFrom, existingAppointmentDateTimeFrom, existingAppointmentDateTimeTill)
            || (!newAppointmentDateTimeTill.isEqual(existingAppointmentDateTimeFrom) && isDatePointInDateRange(newAppointmentDateTimeTill, existingAppointmentDateTimeFrom, existingAppointmentDateTimeTill))
            || isDatePointInDateRange(existingAppointmentDateTimeFrom, newAppointmentDateTimeFrom, newAppointmentDateTimeTill)
            || (!existingAppointmentDateTimeTill.isEqual(newAppointmentDateTimeFrom) && isDatePointInDateRange(existingAppointmentDateTimeTill, newAppointmentDateTimeFrom, newAppointmentDateTimeTill));
    }

    private static boolean isDatePointInDateRange(LocalDateTime point, LocalDateTime x1Inclusive, LocalDateTime x2Exclusive) {
        return !x1Inclusive.isAfter(point) && point.isBefore(x2Exclusive);
    }
}
