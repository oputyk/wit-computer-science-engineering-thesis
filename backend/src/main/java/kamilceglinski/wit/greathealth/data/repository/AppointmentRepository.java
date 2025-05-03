package kamilceglinski.wit.greathealth.data.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import kamilceglinski.wit.greathealth.data.entity.AppointmentEntity;
import kamilceglinski.wit.greathealth.data.entity.DoctorEntity;
import kamilceglinski.wit.greathealth.data.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, String> {
    List<AppointmentEntity> findByPatient(PatientEntity patient);

    Optional<AppointmentEntity> findByPatientAndUuid(PatientEntity patient, String uuid);

    List<AppointmentEntity> findByDoctor(DoctorEntity doctor);

    Optional<AppointmentEntity> findByDoctorAndUuid(DoctorEntity patient, String uuid);

    @Query("select a from AppointmentEntity a where a.dateTimeFrom >= :availabilityDateTimeFrom and a.dateTimeFrom <= :availabilityDateTimeTill and (a.status = kamilceglinski.wit.greathealth.data.entity.StatusEnum.CREATED or a.status = kamilceglinski.wit.greathealth.data.entity.StatusEnum.FINISHED)")
    List<AppointmentEntity> findByAvailabilityTime(LocalDateTime availabilityDateTimeFrom, LocalDateTime availabilityDateTimeTill);
}
