package kamilceglinski.wit.greathealth.data.repository;

import kamilceglinski.wit.greathealth.data.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, String> {
}
