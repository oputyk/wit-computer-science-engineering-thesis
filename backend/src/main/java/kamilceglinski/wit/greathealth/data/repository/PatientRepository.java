package kamilceglinski.wit.greathealth.data.repository;

import java.util.Optional;
import kamilceglinski.wit.greathealth.data.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, String> {
    void deleteByUuid(String uuid);
    Optional<PatientEntity> findByEmail(String email);
}
