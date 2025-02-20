package kamilceglinski.wit.greathealth.data.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import kamilceglinski.wit.greathealth.data.entity.AppointmentEntity;
import kamilceglinski.wit.greathealth.data.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, String> {
    List<AppointmentEntity> findByPatient(PatientEntity patient);
    Optional<AppointmentEntity> findByPatientAndUuid(PatientEntity patient, String uuid);

    List<AppointmentEntity> findByDateTimeFromGreaterThanOrEqualAndDateTimeFromLessThanOrEqualThan(LocalDateTime availabilityDateTimeFrom,
                                                                                                   LocalDateTime availabilityDateTimeTill);
}
