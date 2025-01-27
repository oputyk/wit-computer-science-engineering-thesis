package kamilceglinski.wit.greathealth.data.repository;

import java.util.List;
import java.util.Optional;
import kamilceglinski.wit.greathealth.data.entity.AvailabilityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailabilityRepository extends JpaRepository<AvailabilityEntity, String> {
    List<AvailabilityEntity> findAllByDoctor_uuid(String doctorUuid);

    Optional<AvailabilityEntity> findByDoctor_uuidAndUuid(String doctorUuid, String uuid);
}
