package kamilceglinski.wit.greathealth.data.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import kamilceglinski.wit.greathealth.data.entity.AvailabilityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailabilityRepository extends JpaRepository<AvailabilityEntity, String> {
    List<AvailabilityEntity> findAllByDoctor_uuid(String doctorUuid);

    Optional<AvailabilityEntity> findByDoctor_uuidAndUuid(String doctorUuid, String uuid);

    @Query(value = "select a from AvailabilityEntity a where a.doctor.uuid = :doctorUuid and a.dateTimeFrom <= :fromDateTime and a.dateTimeTill >= :tillDateTime")
    List<AvailabilityEntity> findByDoctorUuidAndDateTime(String doctorUuid, LocalDateTime fromDateTime, LocalDateTime tillDateTime);

    void deleteByDoctor_uuidAndUuid(String doctorUuid, String uuid);
}
