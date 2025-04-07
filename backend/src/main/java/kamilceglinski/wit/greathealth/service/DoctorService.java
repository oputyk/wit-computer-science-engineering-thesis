package kamilceglinski.wit.greathealth.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import kamilceglinski.wit.greathealth.data.entity.AppointmentEntity;
import kamilceglinski.wit.greathealth.data.entity.DoctorEntity;
import kamilceglinski.wit.greathealth.data.entity.DoctorSpecialtyEntity;
import kamilceglinski.wit.greathealth.data.entity.SpecialtyEntity;
import kamilceglinski.wit.greathealth.data.entity.StatusEnum;
import kamilceglinski.wit.greathealth.data.repository.AppointmentRepository;
import kamilceglinski.wit.greathealth.data.repository.DoctorRepository;
import kamilceglinski.wit.greathealth.data.repository.DoctorSpecialtyRepository;
import kamilceglinski.wit.greathealth.data.repository.SpecialtyRepository;
import kamilceglinski.wit.greathealth.dto.AppointmentResponseDTO;
import kamilceglinski.wit.greathealth.dto.DoctorRequestDTO;
import kamilceglinski.wit.greathealth.dto.DoctorResponseDTO;
import kamilceglinski.wit.greathealth.dto.DoctorSpecialtyRequestDTO;
import kamilceglinski.wit.greathealth.dto.DoctorSpecialtyResponseDTO;
import kamilceglinski.wit.greathealth.mapper.AppointmentMapper;
import kamilceglinski.wit.greathealth.mapper.DoctorMapper;
import kamilceglinski.wit.greathealth.mapper.DoctorSpecialtyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Transactional
@RequiredArgsConstructor
@Service
public class DoctorService {

    private final DoctorMapper doctorMapper;
    private final DoctorRepository doctorRepository;
    private final SpecialtyRepository specialtyRepository;
    private final DoctorSpecialtyRepository doctorSpecialtyRepository;
    private final DoctorSpecialtyMapper doctorSpecialtyMapper;
    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;

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

    public List<DoctorResponseDTO> getAllDoctors(String specialtyUuid) {
        List<DoctorEntity> doctors;
        if (specialtyUuid != null) {
            doctors = doctorRepository.findBySpecialty(specialtyUuid);
        } else {
            doctors = doctorRepository.findAll();
        }
        return doctors.stream()
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

    public DoctorSpecialtyResponseDTO createDoctorSpecialty(DoctorSpecialtyRequestDTO requestDTO, String uuid) {
        DoctorEntity doctorEntity = doctorRepository.findById(uuid).orElseThrow();
        SpecialtyEntity specialtyEntity = specialtyRepository.findById(requestDTO.getSpecialtyUuid()).orElseThrow();
        DoctorSpecialtyEntity doctorSpecialtyEntity = new DoctorSpecialtyEntity();
        doctorSpecialtyEntity.setDoctor(doctorEntity);
        doctorSpecialtyEntity.setSpecialty(specialtyEntity);
        DoctorSpecialtyEntity savedDoctorSpecialtyEntity = doctorSpecialtyRepository.save(doctorSpecialtyEntity);
        return doctorSpecialtyMapper.toDoctorResponseDTO(savedDoctorSpecialtyEntity);
    }

    public List<AppointmentResponseDTO> getAppointments(String doctorUuid) {
        DoctorEntity doctorEntity = doctorRepository.findById(doctorUuid)
            .orElseThrow();
        return appointmentRepository.findByDoctor(doctorEntity).stream()
            .map(appointmentMapper::toAppointmentResponseDTO)
            .collect(Collectors.toList());
    }

    public AppointmentResponseDTO finishAppointment(String doctorUuid, String appointmentUuid) {
        DoctorEntity doctorEntity = doctorRepository.findById(doctorUuid)
            .orElseThrow();
        AppointmentEntity appointmentEntity = appointmentRepository.findByDoctorAndUuid(doctorEntity, appointmentUuid)
            .orElseThrow();
        if (!StatusEnum.CREATED.equals(appointmentEntity.getStatus())) {
            throw new RuntimeException("Status of appointment needs to 'CREATED' in order to get it finished");
        }
        appointmentEntity.setStatus(StatusEnum.FINISHED);
        AppointmentEntity savedAppointmentEntity = appointmentRepository.save(appointmentEntity);
        return appointmentMapper.toAppointmentResponseDTO(savedAppointmentEntity);
    }

    public void deleteDoctor(String uuid) {
        doctorRepository.deleteByUuid(uuid);
    }

    public void deleteDoctorSpecialty(String uuid, String specialtyUuid) {
        doctorSpecialtyRepository.deleteByDoctor_uuidAndSpecialty_uuid(uuid, specialtyUuid);
    }
}
